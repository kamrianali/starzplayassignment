package com.starzplay.assignment.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.starzplay.assignment.model.SearchResultsDataModel

sealed class UiEvents {
    object ShowLoading : UiEvents()
    data class PopulateSearchResult(val searchResults: List<SearchResultsDataModel>) : UiEvents()
    object FailureNoResult : UiEvents()
}

enum class MediaTypes(name: String) {
    movie("movie"),
    tv("tv")
}
fun Context.dismissKeyboard(view: View?) {
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}
