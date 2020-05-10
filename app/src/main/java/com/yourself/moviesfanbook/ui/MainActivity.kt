package com.yourself.moviesfanbook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yourself.moviesfanbook.R
import com.yourself.moviesfanbook.data.Movie
import com.yourself.moviesfanbook.data.MovieDetails
import com.yourself.moviesfanbook.repository.ApiResult
import com.yourself.moviesfanbook.repository.Error
import com.yourself.moviesfanbook.repository.Loading
import com.yourself.moviesfanbook.repository.Success

class MainActivity : AppCompatActivity(),ActionBarCallBack {
    private lateinit var toolbar: Toolbar
    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(
                R.id.container,
                MovieListFragment.newInstance(),
                MovieListFragment.TAG
            ).commitNow()

        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        viewModel.selectedMovie.observe(this, teamSelectedObserver)
        viewModel.movieDetailState.observe(this, movieDetailsStateObserver)

    }

    private val teamSelectedObserver = Observer<Movie> {
        it?.let {
            viewModel.fetchMovieDetails()
        }
    }

    private val movieDetailsStateObserver = Observer<ApiResult<MovieDetails>> { state ->
        when (state) {
            is Success<MovieDetails> -> {
                viewModel.movieDetails = state.data
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MovieDetailsFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
            is Loading -> {

            }
            is Error -> {

            }
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showHideActionBarWith(title: String?, showBackButton: Boolean) {
        toolbar.title = title
        if(showBackButton) {
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        }else{
            toolbar.setNavigationIcon(R.mipmap.ic_app_launcher_foreground)
        }
    }

}
