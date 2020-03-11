package com.digitalmidges.androidtest101.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.digitalmidges.androidtest101.db.MoviesTypeConverters
import com.digitalmidges.androidtest101.db.VideosTypeConverters
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created with care by odedfunt on 09/03/2020.
 *
 * TODO: Add a class header comment!
 */
//
@Entity(tableName = "movie_videos_table")
class MovieVideosResponse {
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    @SerializedName("id")
    lateinit var movieId: String

    @TypeConverters(value = [VideosTypeConverters::class])
    @ColumnInfo(name = "videos_list")
    @SerializedName("results")
    var results = ArrayList<MovieVideosItem>()
}


class MovieVideosItem {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    lateinit var id: String


    @ColumnInfo(name = "key")
    @SerializedName("key")
    var key: String? = null

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String? = null

    @ColumnInfo(name = "site")
    @SerializedName("site")
    var site: String? = null

    @ColumnInfo(name = "type")
    @SerializedName("type")
    var type: String? = null

    @ColumnInfo(name = "size")
    @SerializedName("size")
    var size: Int? = null
}
