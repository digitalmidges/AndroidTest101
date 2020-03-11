package com.digitalmidges.androidtest101.api

import androidx.lifecycle.LiveData
import com.digitalmidges.androidtest101.utils.DISCOVER_MOVIES_URL
import com.digitalmidges.androidtest101.utils.GET_MOVIE_VIDEOS
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created with care by odedfunt on 09/03/2020.
 *
 * TODO: Add a class header comment!
 */
 interface MoviesApi {



    @GET(DISCOVER_MOVIES_URL)
    fun getDiscoverMovies(@Query("api_key") api_key: String, @Query("filter_url") filter_url: String, @Query("page") page_number: String): LiveData<ApiResponse<DiscoverMoviesResponse>>

//    @GET(GET_MOVIE_VIDEOS)
//    fun getMoviesVideo(@Path("api_key") api_key:String, @Query("filter_url") filter_url: String): LiveData<ApiResponse<MovieVideoPojoResponse>>

    @GET(GET_MOVIE_VIDEOS)
    fun getMoviesVideo(@Path("movie_id") movie_id:String, @Query("api_key") api_key: String,@Query("filter_url") filter_url: String): LiveData<ApiResponse<MovieVideosResponse>>



}