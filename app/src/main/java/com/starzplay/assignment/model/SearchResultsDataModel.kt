package com.starzplay.assignment.model

import com.starzplay.networking.models.Results

data class SearchResultsDataModel(
    val mediaType: String,
    val result: List<Results>
)
