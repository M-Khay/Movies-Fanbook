package com.yourself.moviesfanbook.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yourself.moviesfanbook.R
import com.yourself.moviesfanbook.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var actionBarListener: ActionBarCallBack
    private lateinit var viewModel: MovieListViewModel
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
        viewModel = ViewModelProvider(requireActivity()).get(MovieListViewModel::class.java)
        binding.lifecycleOwner = this
        binding.movieDetails = viewModel.movieDetails
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieDetailsFragment()
    }

    override fun onResume() {
        super.onResume()
        actionBarListener.showHideActionBarWith(viewModel.selectedMovie.value?.title, true)
    }


}
