package com.digitalmidges.androidtest101.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.digitalmidges.androidtest101.api.DiscoverMovieInfo
import com.digitalmidges.androidtest101.api.MovieVideosResponse

/**
 * Created with care by odedfunt on 09/03/2020.
 *
 * TODO: Add a class header comment!
 */
@Database(entities = [DiscoverMovieInfo::class, MovieVideosResponse::class], version = 1)
@TypeConverters(value = [MoviesTypeConverters::class])
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

}