package com.yourself.moviesfanbook.repository

import com.yourself.moviesfanbook.data.MovieDetails
import com.yourself.moviesfanbook.data.MoviesListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieSearchApi {
    @GET("/")
    suspend fun getMovieListWith(@Query("apikey") apiKey: String, @Query("page") pageNumber: Int,
                                 @Query("s") searchTerm: String): MoviesListResponse

    @GET("/")
    suspend fun getMovieDetailsFor(@Query("apikey") apiKey: String, @Query("plot") plot: String,
                                 @Query("t") searchTerm: String): MovieDetails
}