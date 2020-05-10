package com.yourself.moviesfanbook.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yourself.moviesfanbook.R
import com.yourself.moviesfanbook.data.Movie
import com.yourself.moviesfanbook.data.MovieDetails
import com.yourself.moviesfanbook.databinding.FragmentMovieDetailsBinding
import com.yourself.moviesfanbook.repository.ApiResult
import com.yourself.moviesfanbook.repository.Error
import com.yourself.moviesfanbook.repository.Loading
import com.yourself.moviesfanbook.repository.Success
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var actionBarListener :ActionBarCallBack
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
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_details,
            container,
            false
        )
        val viewModel = ViewModelProvider(requireActivity()).get(MovieListViewModel::class.java)
        binding.lifecycleOwner = this
        viewModel.getMovieDetails()
        viewModel.movieDetails.observe(this.viewLifecycleOwner, teamListObserver)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieDetailsFragment()
    }

    override fun onResume() {
        super.onResume()
        actionBarListener.showHideActionBarWith("d",true)
    }

    private val teamListObserver = Observer<ApiResult<MovieDetails>> { state ->
        when (state) {
            is Success<MovieDetails> -> {
                binding.movieDetails = state.data
            }
            is Loading -> {
            }
            is Error -> {
            }
        }
    }

}
