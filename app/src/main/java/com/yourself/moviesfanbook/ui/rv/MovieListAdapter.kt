package com.yourself.moviesfanbook.ui.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.yourself.moviesfanbook.data.Movie
import com.yourself.moviesfanbook.databinding.MovieListItemBinding


class MovieListAdapter:
    PagedListAdapter<Movie,MovieListViewHolder>((DIFF_CALLBACK)) {
    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Movie>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldMovie: Movie,
                                         newMovie: Movie) = oldMovie.imdbId == newMovie.imdbId

            override fun areContentsTheSame(oldMovie: Movie,
                                            newMovie: Movie) = oldMovie == newMovie
        }
    }

    private var movieList = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val teamListItemBinding =
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListViewHolder(teamListItemBinding.root, teamListItemBinding)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.setData(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun updateTeamList(movieList: List<Movie>) {
        this.movieList = movieList.toMutableList()
        notifyDataSetChanged()
    }



}
