package com.yourself.moviesfanbook.repository

import com.yourself.moviesfanbook.data.MovieDetails
import com.yourself.moviesfanbook.data.MoviesListResponse
import com.yourself.moviesfanbook.utils.Constant.API_KEY
import com.yourself.moviesfanbook.utils.Constant.PLOT

class MovieRepositoryImpl(private val movieSearchApi: MovieSearchApi) : MovieRepository {
    override suspend fun getMovieListWith(movieName: String, pageNumber: Int): MoviesListResponse =
        movieSearchApi.getMovieListWith( API_KEY, pageNumber, movieName)

    override suspend fun getMovieDetailsFor(movieName: String) = movieSearchApi.getMovieDetailsFor(API_KEY,PLOT,movieName)
}