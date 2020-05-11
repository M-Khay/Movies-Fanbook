package com.yourself.moviesfanbook.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yourself.moviesfanbook.R
import com.yourself.moviesfanbook.data.MovieDetails
import com.yourself.moviesfanbook.repository.ApiResult
import com.yourself.moviesfanbook.repository.Error
import com.yourself.moviesfanbook.repository.Loading
import com.yourself.moviesfanbook.repository.Success
import kotlinx.android.synthetic.main.fragment_movie_details.*

class MovieDetailsFragment : Fragment() {

    private lateinit var actionBarListener: ActionBarCallBack
    private lateinit var viewModel: MovieViewModel

    companion object {
        @JvmStatic
        fun newInstance() = MovieDetailsFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActionBarCallBack) {
            actionBarListener = context
        } else {
            throw ClassCastException("$context must implement ActionBarCallBack")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(
            R.layout.fragment_movie_details,
            container,
            false
        )
        viewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        viewModel.fetchMovieDetails()
        viewModel.movieDetailState.observe(viewLifecycleOwner, movieDetailsStateObserver)
        return root
    }

    override fun onResume() {
        super.onResume()
        actionBarListener.showHideActionBarWith(viewModel.selectedMovie.value?.title, true)
    }

    private val movieDetailsStateObserver = Observer<ApiResult<MovieDetails>> { state ->
        when (state) {
            is Success<MovieDetails> -> {
                showMoveDetails(state.data)
                main_content.visibility = View.VISIBLE
                loading_content.visibility = View.GONE
            }
            is Loading -> {
                loading_content.visibility = View.VISIBLE
                main_content.visibility = View.GONE
            }
            is Error -> {
                main_content.visibility = View.GONE
                loading_content.visibility = View.GONE
                Toast.makeText(
                    context,
                    resources.getString(R.string.movie_details_not_found),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showMoveDetails(movieDetails: MovieDetails) {
        director.text = "Director : " + movieDetails.director
        year.text = "Year : " + movieDetails.year
        plot.text = movieDetails.plot
        Glide.with(poster.context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .fitCenter()
            ).load(movieDetails.poster)
            .into(poster)

    }


}
