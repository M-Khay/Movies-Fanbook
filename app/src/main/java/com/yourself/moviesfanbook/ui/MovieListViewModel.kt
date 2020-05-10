package com.yourself.moviesfanbook.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourself.moviesfanbook.data.Movie
import com.yourself.moviesfanbook.data.MovieDetails
import com.yourself.moviesfanbook.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieListViewModel : ViewModel() {
    private val TAG = MovieListViewModel::class.java.name

    @Inject
    lateinit var repository: MovieRepository

    var movieListState = MutableLiveData<ApiResult<List<Movie>>>()
    var selectedMovie = MutableLiveData<Movie>()

    var movieDetailState = MutableLiveData<ApiResult<MovieDetails>>()
    lateinit var movieDetails : MovieDetails

    init {
        movieListState.value =
            Success(emptyList(), 0, false)
    }

    fun getMovieListFor(movieName: String, pageNumber: Int = 1) {
        if (pageNumber == 1)
            movieListState.value = Loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getMovieListWith(movieName, pageNumber)
                withContext(Dispatchers.Main) {
                    if (result.teamListResponse != null) {
                        movieListState.value =
                            Success(
                                result.teamListResponse!!,
                                Integer.valueOf(result.totalResults!!),
                                false
                            )
                    } else {
                        movieListState.value =
                            Error(null, result.error, false)
                    }

                }
            } catch (exception: Exception) {
                Log.d(TAG, "Error from API ${exception.localizedMessage}")
                withContext(Dispatchers.Main) {
                    movieListState.value =
                        Error(exception, null, false)
                }
            }

        }
    }


    fun setSelectedMovie(selectedMovie: Movie) {
        this.selectedMovie.value = selectedMovie
    }
//
//    fun getSelectedMovie(): Movie? {
//        return selectedMovie.value
//    }

    fun fetchMovieDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getMovieDetailsFor(selectedMovie.value!!.title)
                withContext(Dispatchers.Main) {
                    movieDetailState.value = Success(result, null, false)
                }
            } catch (exception: Exception) {
                Log.d(TAG, "Error from API ${exception.localizedMessage}")
                withContext(Dispatchers.Main) {
                    movieListState.value = Error(exception, null, false)
                }
            }

        }

    }


}