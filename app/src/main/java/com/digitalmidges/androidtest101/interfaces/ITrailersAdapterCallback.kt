package com.digitalmidges.androidtest101.interfaces

import com.digitalmidges.androidtest101.api.MovieVideosItem

/**
 * Created with care by odedfunt on 09/03/2020.
 *
 * TODO: Add a class header comment!
 */
interface ITrailersAdapterCallback {

    fun onRowClick(item: MovieVideosItem)
}