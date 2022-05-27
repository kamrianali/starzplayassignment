package com.starzplay.networking.helpers

import okhttp3.ResponseBody

sealed class ApiResponse<out T> {
    object Ideal : ApiResponse<Nothing>()
    data class Success<out T>(val value: T) : ApiResponse<T>()
    data class Failure(
        val isNetworkError: Boolean = false,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : ApiResponse<Nothing>()

    object Loading : ApiResponse<Nothing>()
}
