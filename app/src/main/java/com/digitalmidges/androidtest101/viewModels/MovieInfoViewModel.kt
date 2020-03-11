package com.digitalmidges.androidtest101.viewModels

import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalmidges.androidtest101.api.DiscoverMovieInfo
import com.digitalmidges.androidtest101.api.MovieVideosResponse
import com.digitalmidges.androidtest101.repository.MoviesRepository
import com.digitalmidges.androidtest101.utils.Resource
import javax.inject.Inject


/**
 * Created with care by odedfunt on 08/03/2020.
 *
 * TODO: Add a class header comment!
 */
class MovieInfoViewModel @Inject constructor(
    private val application: Application,
    private val moviesRepository: MoviesRepository
) : ViewModel() {


//    private val _youtubeIntent = SingleLiveEvent<Intent>()
//    val youtubeIntent: LiveData<Intent> = _youtubeIntent

    var movieInfo: LiveData<DiscoverMovieInfo> = MutableLiveData()

    fun getMovieById(movieId: Int?) {
        movieInfo = moviesRepository.getMovieById(movieId)
    }


    var movieVideosList: LiveData<Resource<MovieVideosResponse>> = MutableLiveData()


    fun getMovieVideosList(movieId: Int?) {
        movieVideosList = moviesRepository.getMovieVideosByMovieId(movieId)
    }

    fun openYouTube(key: String?) {
        if (key == null) {
            return
        }

        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key"))
        appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$key")

        )

/*        val manager: PackageManager = application.packageManager
        val infos = manager.queryIntentActivities(appIntent, 0)
        if (infos.size > 0) {
            _youtubeIntent.postValue(appIntent)
        } else {
            _youtubeIntent.postValue(webIntent)
            //No Application can handle your intent
        }*/

        webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            application.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            application.startActivity(webIntent)
        }

    }


}