package com.digitalmidges.androidtest101.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.digitalmidges.androidtest101.AppExecutors
import com.digitalmidges.androidtest101.api.*
import com.digitalmidges.androidtest101.db.MoviesDao
import com.digitalmidges.androidtest101.utils.*
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created with care by odedfunt on 09/03/2020.
 *
 * TODO: Add a class header comment!
 */
@Singleton
class MoviesRepository @Inject constructor(
    private val application: Application,
    private val appExecutors: AppExecutors,
    private val moviesDao: MoviesDao,
    private val moviesApi: MoviesApi
) {

    private val TAG = "MoviesRepository"

    private var isFetchedMoviesForTheFirstTime = false
    private var lastFetchedPage = 0

    fun loadMovies(page: Int): LiveData<Resource<List<DiscoverMovieInfo>>>{
        return object : NetworkBoundResource<List<DiscoverMovieInfo>, DiscoverMoviesResponse>(appExecutors) {
            override fun saveCallResult(item: DiscoverMoviesResponse) {
                for (loopItem in item.results) {
                    loopItem.page = page
                    loopItem.totalResults = item.total_results
                }
                moviesDao.insertMovies(item.results)
            }

            override fun shouldFetch(data: List<DiscoverMovieInfo>?): Boolean {
//                return data == null || data.isEmpty()
//                return GeneralMethods.isInternetAvailable(application) // if we don't have internet, fetch from db

                return if (GeneralMethods.isInternetAvailable(application) && !isFetchedMoviesForTheFirstTime){
                    isFetchedMoviesForTheFirstTime = true
                    true
                }else{
                    data == null || data.isEmpty()
                }

            }

            override fun loadFromDb(): LiveData<List<DiscoverMovieInfo>>{
                return moviesDao.getAllMovies(page)
            }

            override fun createCall() = moviesApi.getDiscoverMovies(API_KEY,FILTER_URL,page.toString())

            override fun onFetchFailed() {
                Log.d(TAG, "onFetchFailed: ")
            }

        }.asLiveData()
    }

    fun getMovieById(movieId: Int?): LiveData<DiscoverMovieInfo> {
        if (movieId != null) {
            return moviesDao.getMovieById(movieId)
        }
        return AbsentLiveData.create()
    }



    fun getMovieVideosByMovieId(movieId: Int?): LiveData<Resource<MovieVideosResponse>>{
        if (movieId != null) {
            return object :
                NetworkBoundResource< MovieVideosResponse,MovieVideosResponse>(appExecutors) {
                override fun saveCallResult(item: MovieVideosResponse) {


                    moviesDao.insertMovieVideos(item)
                }

                //                override fun loadFromDb() = moviesDao.getAllMovies()
                override fun loadFromDb() = moviesDao.getAllMovieVideos(movieId)

                override fun createCall() = moviesApi.getMoviesVideo(movieId.toString(),API_KEY,FILTER_URL)


                override fun shouldFetch(data: MovieVideosResponse?): Boolean {
                    return data == null
                }


            }.asLiveData()
        }else{
            return AbsentLiveData.create()
        }
    }



}