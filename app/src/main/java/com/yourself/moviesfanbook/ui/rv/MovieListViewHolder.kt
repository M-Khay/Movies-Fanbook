package com.yourself.moviesfanbook.ui.rv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yourself.moviesfanbook.data.Movie
import com.yourself.moviesfanbook.databinding.MovieListItemBinding


class MovieListViewHolder constructor(teamListItemView: View, private val teamListItemBinding: MovieListItemBinding) :
    RecyclerView.ViewHolder(teamListItemView) {

    fun setData(movie: Movie) {
        teamListItemBinding.movieDetails = movie
    }

}