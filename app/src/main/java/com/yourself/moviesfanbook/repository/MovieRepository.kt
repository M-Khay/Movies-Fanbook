package com.yourself.moviesfanbook.repository

import com.yourself.moviesfanbook.data.MovieDetails
import com.yourself.moviesfanbook.data.MoviesListResponse

interface MovieRepository {
    suspend fun getMovieListWith(movieName : String, pageNumber :Int):MoviesListResponse
    suspend fun getMovieDetailsFor(movieName : String):MovieDetails
}