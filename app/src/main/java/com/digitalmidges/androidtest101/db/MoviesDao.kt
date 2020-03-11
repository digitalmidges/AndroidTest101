package com.digitalmidges.androidtest101.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.digitalmidges.androidtest101.api.DiscoverMovieInfo
import com.digitalmidges.androidtest101.api.MovieVideosResponse


/**
 * Created with care by odedfunt on 09/03/2020.
 *
 * TODO: Add a class header comment!
 */
@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(resultModel: DiscoverMovieInfo)
//    ORDER BY id ASC
    @Query("SELECT * from movies_table WHERE page = :page_number")
    fun getAllMovies(page_number: Int): LiveData<List<DiscoverMovieInfo>>

    @Query("SELECT * from movies_table WHERE id = :movieId")
    fun getMovieById(movieId:Int): LiveData<DiscoverMovieInfo>

    @Query("DELETE FROM movies_table")
    fun deleteAll()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(moviesList: List<DiscoverMovieInfo>)


    @Query("SELECT * from movie_videos_table WHERE movie_id = :movieId")
    fun getAllMovieVideos(movieId:Int): LiveData<MovieVideosResponse>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertMovieVideos(videosItem: MovieVideosResponse)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieVideos(videosResponse: MovieVideosResponse)

}