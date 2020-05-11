package com.yourself.moviesfanbook.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yourself.moviesfanbook.R
import com.yourself.moviesfanbook.data.Movie
import com.yourself.moviesfanbook.di.ComponentInjector
import com.yourself.moviesfanbook.repository.ApiResult
import com.yourself.moviesfanbook.repository.Error
import com.yourself.moviesfanbook.repository.Loading
import com.yourself.moviesfanbook.repository.Success
import com.yourself.moviesfanbook.ui.rv.EndlessRecyclerViewScrollListener
import com.yourself.moviesfanbook.ui.rv.MovieListAdapter
import com.yourself.searchyourcityweather.utils.NetworkConnectivity
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : Fragment() {

    companion object {
        val TAG = MovieListFragment::class.java.name
        @JvmStatic
        fun newInstance() = MovieListFragment()
    }

    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: MovieListAdapter
    private lateinit var onScrollListener: EndlessRecyclerViewScrollListener
    private lateinit var actionBarListener: ActionBarCallBack

    private var movieList = mutableSetOf<Movie>()
    private var lastPageFetched: Int = 0

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
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java).also {
            ComponentInjector.component.inject(it)
        }

        viewModel.movieListState.observe(this.viewLifecycleOwner, teamListObserver)
        search_go.setOnClickListener {
            searchMovie()
        }

        search_text.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                v.clearFocus()
                searchMovie()
            }
            true
        }

        adapter = MovieListAdapter(viewModel)
        rv_movie_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@MovieListFragment.adapter
        }

        rv_movie_list.addItemDecoration(
            DividerItemDecoration(
                rv_movie_list.context,
                DividerItemDecoration.VERTICAL
            )
        )
        onScrollListener = object :
            EndlessRecyclerViewScrollListener(rv_movie_list.layoutManager as LinearLayoutManager?) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                // Triggered only when new data needs to be appended to the list
                if (lastPageFetched < page) {
                    lastPageFetched = page
                    searchMoreMovieList(page)
                }
            }
        }
        rv_movie_list.addOnScrollListener(onScrollListener)

    }


    override fun onResume() {
        super.onResume()
        actionBarListener.showHideActionBarWith(resources.getString(R.string.app_name), false)

    }

    private fun searchMovie() {
        hideKeyboard()
        clearPreviousSearchedData()
        val searchText = search_text.text.toString()

        if (!searchText.isNullOrEmpty()) {
            input_layout.error = ""
            if (NetworkConnectivity.isNetworkConnected) {
                viewModel.getMovieListFor(searchText)
                Log.d(TAG, "Searched Team : $searchText")
            } else {
                showAlertDialog(resources.getString(R.string.network_error_title), resources.getString(R.string.network_error_message),
                    resources.getString(R.string.alert_dialog_ok)
                )
            }
        } else {
            input_layout.error = resources.getString(R.string.empty_movie_name)
            Toast.makeText(activity, "Search Term Cannot be empty", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearPreviousSearchedData() {
        adapter.clearLastSearchedItems()
        movieList.clear()
        lastPageFetched = 0
        onScrollListener.resetState()
    }

    private fun searchMoreMovieList(pageNumber: Int) {
        if (adapter.itemCount < adapter.getTotalListITem()) {
            val searchText = search_text.text.toString()
            if (NetworkConnectivity.isNetworkConnected) {
                viewModel.getMovieListFor(searchText, pageNumber)
                Log.d(TAG, "Searched Team : $searchText")
            } else {
                showAlertDialog(
                    resources.getString(R.string.network_error_title),
                    resources.getString(R.string.network_error_message),
                    resources.getString(R.string.alert_dialog_ok)
                )
            }
        }
    }

    private val teamListObserver = Observer<ApiResult<List<Movie>>> { state ->
        when (state) {
            is Success<List<Movie>> -> {
                loading_content.visibility = View.GONE
                api_error_response.visibility = View.GONE
                rv_movie_list.visibility = View.VISIBLE
                if (!movieList.containsAll(state.data)) {
                    adapter.updateTeamList(state.data)
                    movieList.addAll(state.data)
                } else {
                    adapter.updateTeamList(movieList.toMutableList())
                }
                adapter.setTotalListItem(state.totalListItem!!)
            }
            is Loading -> {
                api_error_response.visibility = View.GONE
                loading_content.visibility = View.VISIBLE
            }
            is Error -> {
                loading_content.visibility = View.GONE
                rv_movie_list.visibility = View.GONE
                api_error_response.text = state.apiErrorMessage ?: state.exception.toString()
                api_error_response.visibility = View.VISIBLE
            }
        }
    }

    private fun showAlertDialog(title: String, message: String, positiveButtonText: String?) {
        MaterialAlertDialogBuilder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun hideKeyboard() {
        val keyboard =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}
