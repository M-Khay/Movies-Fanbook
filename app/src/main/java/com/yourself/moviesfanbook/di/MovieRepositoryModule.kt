package com.yourself.moviesfanbook.di

import com.yourself.moviesfanbook.repository.MovieRepository
import com.yourself.moviesfanbook.repository.MovieRepositoryImpl
import com.yourself.moviesfanbook.repository.MovieSearchApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class MovieRepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepositoryModule(movieSearchApi: MovieSearchApi):MovieRepository = MovieRepositoryImpl(movieSearchApi)
}