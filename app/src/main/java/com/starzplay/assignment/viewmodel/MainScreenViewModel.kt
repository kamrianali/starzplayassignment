package com.starzplay.assignment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starzplay.assignment.model.SearchResultsDataModel
import com.starzplay.assignment.utils.UiEvents
import com.starzplay.networking.MainScreenRepository
import com.starzplay.networking.helpers.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val mainScreenRepository: MainScreenRepository
) : ViewModel() {
    private val _eventFlow = MutableSharedFlow<UiEvents>(replay = 1)
    val eventFlow = _eventFlow.asSharedFlow()
    fun getSearchQueryResults(searchQuery: String) {
        viewModelScope.launch {
            mainScreenRepository.getQueryResultsFromRemote(searchQuery)
                .collect { apiResponse ->
                    when (apiResponse) {
                        is ApiResponse.Loading -> {
                            _eventFlow.emit(UiEvents.ShowLoading)
                        }//todo show loading
                        is ApiResponse.Success -> {
                            val finalList = apiResponse.value.results.groupBy {
                                it.mediaType
                            }.map { SearchResultsDataModel(it.key ?: "", it.value) }.sortedBy {
                                it.mediaType
                            }
                            _eventFlow.emit(UiEvents.PopulateSearchResult(finalList))
                            Log.d(
                                "StarzPlayAssignment",
                                "total Result ${apiResponse.value.totalResults}"
                            )
                        }
                        is ApiResponse.Failure -> {
                            Log.d(
                                "StarzPlayAssignment",
                                "total Result ${apiResponse.errorCode}"
                            )
                        }
                        else -> Unit
                    }
                }
        }
    }


}