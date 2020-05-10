package com.yourself.moviesfanbook.data

import com.google.gson.annotations.SerializedName

data class MoviesListResponse(
    @SerializedName("Search")
    var teamListResponse: List<Movie>?,

    var totalResults: String?,

    @SerializedName("Response")
    var response: String,

    @SerializedName("Error")
    var error: String?
)