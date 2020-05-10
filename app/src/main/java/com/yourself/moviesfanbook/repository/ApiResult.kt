package com.yourself.moviesfanbook.repository

sealed class ApiResult<out T : Any> {
    abstract val isLoading: Boolean
}

data class Success<out T : Any>(val data: T, val totalListItem : Int? ,  override val isLoading: Boolean) : ApiResult<T>()
data class Error(val exception: Exception?,val apiErrorMessage : String?, override val isLoading: Boolean) : ApiResult<Nothing>()
data class Loading(override val isLoading: Boolean) : ApiResult<Nothing>()
