package com.yourself.moviesfanbook.data

import com.google.gson.annotations.SerializedName

data class MoviesListResponse(
    @SerializedName("Search")
    var teamListResponse: List<Movie>
)