package com.yourself.moviesfanbook.repository

import com.yourself.moviesfanbook.data.MoviesListResponse
import com.yourself.moviesfanbook.utils.Constant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieSearchApi {
    @GET("/")
    suspend  fun getMovieListWith(@Query("type") type: String = "movie",@Query("apikey") apiKey: String = API_KEY
        ,@Query("page") pageNumber: Int=1,@Query("s") searchTerm: String): MoviesListResponse
}