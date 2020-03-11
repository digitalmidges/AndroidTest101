package com.digitalmidges.androidtest101.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.digitalmidges.androidtest101.db.MoviesTypeConverters
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created with care by odedfunt on 09/03/2020.
 *
 * TODO: Add a class header comment!
 */

class DiscoverMoviesResponse {

    @SerializedName("page")
    var page: Int? = null
    @SerializedName("total_results")
    var total_results: Int? = null
    @SerializedName("total_pages")
    var total_pages: Int? = null
    @SerializedName("results")
    var results = ArrayList<DiscoverMovieInfo>()
}


@Entity(tableName = "movies_table")
class DiscoverMovieInfo {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int ?=null

    @ColumnInfo(name = "page")
    var page: Int?=null

    @ColumnInfo(name = "total_results")
    var totalResults: Int?=null

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    var popularity: Double?=null

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    var voteCount: Int? = null

    @ColumnInfo(name = "video")
    @SerializedName("video")
    var video: Boolean? = null

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String? = null

    @ColumnInfo(name = "adult")
    @SerializedName("adult")
    var adult: Boolean? = null

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    var backdropPath: String? = null

    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    var originalLanguage: String? = null

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    var originalTitle: String? = null

    @ColumnInfo(name = "genre_ids")
    @SerializedName("genre_ids")
    var genreIds:List<Int>? = ArrayList()

    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String? = null

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var voteAverage: Double?=null

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var overview: String? = null

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    var releaseDate: Date? = null


    var isForPagingCell: Boolean = false

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as DiscoverMovieInfo

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (page ?: 0)
        result = 31 * result + (popularity?.hashCode() ?: 0)
        result = 31 * result + (voteCount ?: 0)
        result = 31 * result + (video?.hashCode() ?: 0)
        result = 31 * result + (posterPath?.hashCode() ?: 0)
        result = 31 * result + (adult?.hashCode() ?: 0)
        result = 31 * result + (backdropPath?.hashCode() ?: 0)
        result = 31 * result + (originalLanguage?.hashCode() ?: 0)
        result = 31 * result + (originalTitle?.hashCode() ?: 0)
        result = 31 * result + (genreIds?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (voteAverage?.hashCode() ?: 0)
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        result = 31 * result + isForPagingCell.hashCode()
        return result
    }

}

