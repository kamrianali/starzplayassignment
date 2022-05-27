package com.starzplay.networking.helpers

import android.content.ContentValues.TAG
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend inline fun <T> safeApiCall(
    crossinline responseFunction: suspend () -> T
): ApiResponse<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = responseFunction()
            Log.d(TAG, "safeApiCall: response = $response")
            ApiResponse.Success(response)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Log.e("ApiCalls", "Call error: ${throwable.localizedMessage}", throwable.cause)
            when (throwable) {
                is HttpException -> {
                    ApiResponse.Failure(false, throwable.code(), throwable.response()?.errorBody())
                }
                else -> {
                    ApiResponse.Failure(true, null, null)
                }
            }
        }
    }
}
