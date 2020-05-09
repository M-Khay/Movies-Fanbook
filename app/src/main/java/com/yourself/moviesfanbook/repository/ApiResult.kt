package com.yourself.moviesfanbook.repository

sealed class ApiResult<out T : Any> {
    abstract val isLoading: Boolean
}

data class Success<out T : Any>(val data: T, override val isLoading: Boolean) : ApiResult<T>()
data class Error(val exception: Exception, override val isLoading: Boolean) : ApiResult<Nothing>()
data class Loading(override val isLoading: Boolean) : ApiResult<Nothing>()
