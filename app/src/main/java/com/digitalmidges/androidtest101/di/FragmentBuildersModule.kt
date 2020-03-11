package com.digitalmidges.androidtest101.di

import com.digitalmidges.androidtest101.fragments.HomeFragment
import com.digitalmidges.androidtest101.fragments.MovieInfoFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @HomeScope
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieInfoFragment(): MovieInfoFragment

}
