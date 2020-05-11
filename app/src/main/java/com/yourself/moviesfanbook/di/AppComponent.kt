package com.yourself.searchyourcityweather.di

import com.yourself.moviesfanbook.di.AppModule
import com.yourself.moviesfanbook.di.MovieRepositoryModule
import com.yourself.moviesfanbook.di.NetworkModule
import com.yourself.moviesfanbook.ui.MovieViewModel
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, AppModule::class,
        NetworkModule::class, MovieRepositoryModule::class]
)

interface AppComponent {
    fun inject(movieListViewModel: MovieViewModel)
}


