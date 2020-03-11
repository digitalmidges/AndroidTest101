package com.digitalmidges.androidtest101.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.digitalmidges.androidtest101.viewModels.MoviesViewModelFactory
import com.digitalmidges.androidtest101.viewModels.HomeViewModel
import com.digitalmidges.androidtest101.viewModels.MainViewModel
import com.digitalmidges.androidtest101.viewModels.MovieInfoViewModel


import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MovieInfoViewModel::class)
    abstract fun bindMovieInfoViewModel(movieInfoViewModel: MovieInfoViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: MoviesViewModelFactory): ViewModelProvider.Factory
}
