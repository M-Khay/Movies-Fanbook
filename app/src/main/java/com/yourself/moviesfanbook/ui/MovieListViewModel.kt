package com.yourself.moviesfanbook.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourself.moviesfanbook.data.Movie
import com.yourself.moviesfanbook.repository.ApiResult
import com.yourself.moviesfanbook.repository.Error
import com.yourself.moviesfanbook.repository.Loading
import com.yourself.moviesfanbook.repository.MovieRepository
import com.yourself.moviesfanbook.repository.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieListViewModel :ViewModel(){
    private val TAG = MovieListViewModel::class.java.name

    @Inject
    lateinit var repository: MovieRepository

    var movieListState = MutableLiveData<ApiResult<List<Movie>>>()

    init {
        movieListState.value =
            Success(emptyList(), false)
    }

    fun getMovieListFor(movieName: String) {
        movieListState.value = Loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getMovieListWith(movieName)
                withContext(Dispatchers.Main) {
                    movieListState.value =
                        Success(
                            result.teamListResponse,
                            false
                        )
                }
            } catch (exception: Exception) {
                Log.d(TAG, "Error from API ${exception.localizedMessage}")
                withContext(Dispatchers.Main) {
                    movieListState.value =
                        Error(exception, false)
                }
            }
        }
    }
}