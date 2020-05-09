package com.yourself.moviesfanbook.repository

import com.yourself.moviesfanbook.data.MoviesListResponse
import com.yourself.moviesfanbook.utils.Constant.API_KEY

class MovieRepositoryImpl(private val movieSearchApi: MovieSearchApi) : MovieRepository {
    override suspend fun getMovieListWith(movieName: String): MoviesListResponse =
        movieSearchApi.getMovieListWith("movie", API_KEY, 1, movieName)
}