package com.starzplay.assignment.view.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starzplay.assignment.R
import com.starzplay.assignment.databinding.FragmentMainScreenBinding
import com.starzplay.assignment.model.SearchResultsDataModel
import com.starzplay.assignment.utils.UiEvents
import com.starzplay.assignment.utils.dismissKeyboard
import com.starzplay.assignment.view.fragments.main.adapter.MainCarouselAdapter
import com.starzplay.assignment.viewmodel.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainScreenFragment : Fragment() {
    private lateinit var binding: FragmentMainScreenBinding
    private val viewModel by viewModels<MainScreenViewModel>()
    private lateinit var adapter: MainCarouselAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_screen, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        listenToViewModelEvents() // Start Observing events from View Model
        if (::binding.isInitialized) {
            binding.button.setOnClickListener {
                if (binding.searchBar.text.toString().isNotEmpty())
                    viewModel.getSearchQueryResults(binding.searchBar.text.toString())
                requireContext().dismissKeyboard(binding.root)
            }
        }
    }

    private fun prepareRecyclerView() {
        binding.mainCarouselRecycler.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.mainCarouselRecycler.setHasFixedSize(true)
        binding.mainCarouselRecycler.itemAnimator = DefaultItemAnimator()
    }

    private fun listenToViewModelEvents() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect {
                    when (it) {
                        is UiEvents.ShowLoading -> {
                            toggleProgress(
                                progressView = true
                            )
                        }
                        is UiEvents.PopulateSearchResult -> {
                            if (it.searchResults.isNotEmpty()) {
                                toggleProgress(
                                    recyclerView = true
                                )
                                assignDataRecycler(it.searchResults)
                            } else {
                                assignDataRecycler(emptyList())
                                toggleProgress(
                                    noDataView = true
                                )
                            }
                        }
                        is UiEvents.FailureNoResult -> {
                            toggleProgress(
                                noDataView = true
                            )
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun assignDataRecycler(searchResults: List<SearchResultsDataModel>) {
        adapter =
            MainCarouselAdapter(searchResults)
        binding.mainCarouselRecycler.adapter = adapter
    }

    private fun toggleProgress(
        progressView: Boolean = false,
        noDataView: Boolean = false,
        recyclerView: Boolean = false
    ) {
        if (::binding.isInitialized) {
            binding.progress.visibility = if (progressView) View.VISIBLE else View.GONE
            binding.noData.visibility = if (noDataView) View.VISIBLE else View.GONE
            binding.mainCarouselRecycler.visibility = if (recyclerView) View.VISIBLE else View.GONE
        }
    }
}