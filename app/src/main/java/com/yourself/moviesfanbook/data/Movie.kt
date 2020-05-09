package com.yourself.moviesfanbook.data

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName


data class Movie(@SerializedName("Title") var title: String, @SerializedName("Poster") var poster: String,
    @SerializedName("Year") var year: String, @SerializedName("imdbID") var imdbId: String) {
    companion object {
        @JvmStatic
        @BindingAdapter("poster")
        fun loadImage(imageView: ImageView, poster: String) {
            Glide.with(imageView.context)
                .setDefaultRequestOptions(
                    RequestOptions()
                        .fitCenter()
                ).load(poster)
                .into(imageView)
        }
    }
}