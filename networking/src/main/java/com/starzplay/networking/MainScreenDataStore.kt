package com.starzplay.networking

import com.starzplay.networking.helpers.ApiResponse
import com.starzplay.networking.helpers.ApiService
import com.starzplay.networking.helpers.safeApiCall
import com.starzplay.networking.models.MultiSearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


interface MainScreenDataStore {
    suspend fun getQueryResultsFromRemote(queryParam: String): Flow<ApiResponse<MultiSearchResponse>>
}

class MainScreenRepository @Inject constructor(
    private val apiService: ApiService,
) : MainScreenDataStore {
    override suspend fun getQueryResultsFromRemote(queryParam: String): Flow<ApiResponse<MultiSearchResponse>> =
        flow {
            emit(ApiResponse.Loading)
            val result = safeApiCall {
                apiService.getSearchResults(query = queryParam)
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
}