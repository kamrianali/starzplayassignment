package com.starzplay.assignment.view.fragments.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starzplay.assignment.R
import com.starzplay.assignment.databinding.MainCarouselViewHolderItemBinding
import com.starzplay.assignment.model.SearchResultsDataModel
import javax.inject.Inject

class MainCarouselAdapter @Inject constructor(
    var mainDataList: List<SearchResultsDataModel>

) : RecyclerView.Adapter<MainCarouselViewHolder>() {
    private lateinit var dataItem: SearchResultsDataModel
    private lateinit var vhItemMainCarouselBinding: MainCarouselViewHolderItemBinding
    private var recyclerViewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCarouselViewHolder {
        vhItemMainCarouselBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.main_carousel_view_holder_item,
            parent,
            false
        )
        val horizontalLayoutManager =
            LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)
        return MainCarouselViewHolder(vhItemMainCarouselBinding, this, horizontalLayoutManager)
    }

    override fun onBindViewHolder(holder: MainCarouselViewHolder, position: Int) {
        dataItem = mainDataList[position]
        holder.bindToView(dataItem, position)
        val childAdapter = ChildCarouselAdapter(dataItem.result, this)
        holder.binding.childCarouselRecycler.adapter = childAdapter
        holder.binding.childCarouselRecycler.setRecycledViewPool(recyclerViewPool)

    }

    override fun getItemCount(): Int {
        return mainDataList.size
    }

}

class MainCarouselViewHolder(
    val binding: MainCarouselViewHolderItemBinding,
    private val adapter: MainCarouselAdapter,
    horizontalLayoutManager: LinearLayoutManager
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.childCarouselRecycler.layoutManager = horizontalLayoutManager
        binding.childCarouselRecycler.setHasFixedSize(true)
        binding.childCarouselRecycler.setItemViewCacheSize(20)
    }

    fun bindToView(dataItem: SearchResultsDataModel, position: Int) {
        binding.itemdata = dataItem
        binding.clickListener = adapter
        binding.position = position
        binding.executePendingBindings()
    }
}
