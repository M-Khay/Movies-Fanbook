package com.yourself.moviesfanbook.ui.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yourself.moviesfanbook.data.Movie
import com.yourself.moviesfanbook.databinding.MovieListItemBinding
import com.yourself.moviesfanbook.ui.MovieListViewModel


class MovieListAdapter(var movieListViewModel: MovieListViewModel):
    RecyclerView.Adapter<MovieListViewHolder>() {
    private var movieList = mutableListOf<Movie>()
    private var totalListItem: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val teamListItemBinding =
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListViewHolder(teamListItemBinding.root, teamListItemBinding)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.setData(movieList[position])
        holder.itemView.setOnClickListener {
            movieListViewModel.setSelectedMovie(movieList[position])
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun updateTeamList(movieList: List<Movie>) {
        this.movieList.addAll(this.movieList.size, movieList.toMutableList())
        notifyItemRangeInserted(this.movieList.size - movieList.size, movieList.size)
    }

    fun setTotalListItem(total: Int) {
        totalListItem = total
    }

    fun getTotalListITem(): Int {
        return totalListItem
    }

    fun clearLastSearchedItems() {
        this.movieList.clear()
        notifyDataSetChanged()
    }


}
