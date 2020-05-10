package com.yourself.moviesfanbook.data

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName
import com.yourself.moviesfanbook.R


data class MovieDetails(
    @SerializedName("Title") var title: String,
    @SerializedName("Poster") var poster: String,
    @SerializedName("Year") var year: String,
    @SerializedName("Director") var director: String,
    @SerializedName("Plot") var plot: String
) {
    companion object {
        @JvmStatic
        @BindingAdapter("imageView")
        fun loadImage(imageView: ImageView, poster: String) {
            Glide.with(imageView.context)
                .setDefaultRequestOptions(
                    RequestOptions()
                        .fitCenter()
                ).load(poster)
                .placeholder(R.mipmap.ic_default_placeholder_round)
                .into(imageView)
        }
    }
}