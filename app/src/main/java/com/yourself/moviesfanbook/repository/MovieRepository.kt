package com.yourself.moviesfanbook.repository

import com.yourself.moviesfanbook.data.MoviesListResponse

interface MovieRepository {
    suspend fun getMovieListWith(movieName : String):MoviesListResponse
}