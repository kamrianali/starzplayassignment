package com.starzplay.networking.helpers

import com.starzplay.networking.models.MultiSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("3/search/multi")
    suspend fun getSearchResults(
        @Query("api_key") apiKey: String = "3d0cda4466f269e793e9283f6ce0b75e",
        @Query("language") language: String = "en-US",
        @Query("query") query: String,
    ): MultiSearchResponse
}