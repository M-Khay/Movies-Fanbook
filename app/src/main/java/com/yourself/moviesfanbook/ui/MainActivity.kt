package com.yourself.moviesfanbook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yourself.moviesfanbook.R
import com.yourself.moviesfanbook.data.Movie

class MainActivity : AppCompatActivity(),ActionBarCallBack {
    private lateinit var toolbar: Toolbar
    private lateinit var viewModel: MovieViewModel

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

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        viewModel.selectedMovie.observe(this, teamSelectedObserver)

    }

    private val teamSelectedObserver = Observer<Movie> {
        it?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieDetailsFragment.newInstance())
                .addToBackStack(null)
                .commit()
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
