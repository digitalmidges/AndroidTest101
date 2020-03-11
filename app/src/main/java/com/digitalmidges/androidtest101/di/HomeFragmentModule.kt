package com.digitalmidges.androidtest101.di

import com.bumptech.glide.RequestManager
import com.digitalmidges.androidtest101.R
import com.digitalmidges.androidtest101.adapters.GridAdapter
import com.digitalmidges.androidtest101.db.MoviesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HomeFragmentModule {

    @HomeScope
    @Provides
    fun provideAdapter(requestManager: RequestManager): GridAdapter {
        return GridAdapter(requestManager)
    }




}
