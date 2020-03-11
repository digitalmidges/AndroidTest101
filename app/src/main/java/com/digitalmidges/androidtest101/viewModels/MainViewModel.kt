package com.digitalmidges.androidtest101.viewModels

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.*
import com.digitalmidges.androidtest101.api.DiscoverMovieInfo
import com.digitalmidges.androidtest101.api.DiscoverMoviesResponse
import com.digitalmidges.androidtest101.enums.EToolbarStyleType
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
class MainViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {


    private val _toolbarStyle = SingleLiveEvent<EToolbarStyleType>()
    val toolbarStyle: LiveData<EToolbarStyleType> = _toolbarStyle


    private val _toolbarText = SingleLiveEvent<String>()
    val toolbarText: LiveData<String> = _toolbarText


    fun setToolbarStyleHome() {
        _toolbarStyle.postValue(EToolbarStyleType.TOOLBAR_STYLE_HOME)
    }

    fun setToolbarStyleBackButton() {
        _toolbarStyle.postValue(EToolbarStyleType.TOOLBAR_STYLE_BACK_BUTTON)
    }

    fun setToolbarText(text:String) {
        _toolbarText.postValue(text)
    }




}