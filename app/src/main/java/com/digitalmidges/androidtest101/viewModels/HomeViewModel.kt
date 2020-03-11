package com.digitalmidges.androidtest101.viewModels

import android.os.Parcelable
import androidx.lifecycle.*
import com.digitalmidges.androidtest101.api.DiscoverMovieInfo
import com.digitalmidges.androidtest101.repository.MoviesRepository
import com.digitalmidges.androidtest101.utils.AbsentLiveData
import com.digitalmidges.androidtest101.utils.Resource
import com.digitalmidges.androidtest101.utils.SingleLiveEvent
import javax.inject.Inject

/**
 * Created with care by odedfunt on 08/03/2020.
 *
 * TODO: Add a class header comment!
 */
class HomeViewModel @Inject constructor(moviesRepository: MoviesRepository) : ViewModel() {

    private var moviePageToLoad = 0
    private var allFetchedMovies :ArrayList<DiscoverMovieInfo> = ArrayList()

    private var moviePageLiveData: MutableLiveData<Int> = MutableLiveData()
    val movieListLiveData: LiveData<Resource<List<DiscoverMovieInfo>>>

//    var list :ArrayList<DiscoverMovieInfo> = it.data as ArrayList<DiscoverMovieInfo>
//    allFetchedMovies.addAll(it.)
    init {
        moviePageToLoad  = 1
        moviePageLiveData.postValue(moviePageToLoad)

        movieListLiveData = moviePageLiveData.switchMap {

            moviePageLiveData.value?.let { moviesRepository.loadMovies(it) }
                ?: AbsentLiveData.create()
        }

    }

//    fun getMoviesDataByPage(page: Int) {
//        moviePageToLoad  = page
//        moviePageLiveData.postValue(moviePageToLoad)
//    }


    fun addDataToMoviesList(list :ArrayList<DiscoverMovieInfo>){
        allFetchedMovies.addAll(list)
    }

    fun getAllMoviesList():ArrayList<DiscoverMovieInfo>{
        return allFetchedMovies
    }

    fun getMoviesDataByPage() {
        moviePageToLoad ++
        moviePageLiveData.postValue(moviePageToLoad)
    }


    private val _movieListState = SingleLiveEvent<Parcelable>()
    val movieListState: LiveData<Parcelable> = _movieListState

    fun saveListState(onSaveInstanceState: Parcelable?) {
        _movieListState.postValue(onSaveInstanceState)
    }





}