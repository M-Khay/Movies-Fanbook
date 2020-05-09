package com.yourself.moviesfanbook.repository

import androidx.lifecycle.viewModelScope
import androidx.paging.PageKeyedDataSource
import com.yourself.moviesfanbook.data.Movie

class MovieDataSource(private val movieSearchApi: MovieSearchApi) : PageKeyedDataSource<Int, Movie>(){
    //the size of a page that we want
    val PAGE_SIZE = 10
    //we will start from the first page which is 1
    private val FIRST_PAGE = 1

   override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {

        movieSearchApi.getMovieListWith("Abc")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("Not yet implemented")
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("Not yet implemented")
    }


}