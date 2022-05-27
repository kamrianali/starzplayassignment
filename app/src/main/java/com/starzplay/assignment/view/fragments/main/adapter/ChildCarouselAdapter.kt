package com.starzplay.assignment.view.fragments.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.starzplay.assignment.R
import com.starzplay.assignment.databinding.ItemImageViewHolderBinding
import com.starzplay.assignment.view.fragments.main.MainScreenFragmentDirections
import com.starzplay.networking.models.Results
import javax.inject.Inject

class ChildCarouselAdapter @Inject constructor(
    var mainDataList: List<Results>,
    val mainCarouselAdapter: MainCarouselAdapter

) : RecyclerView.Adapter<ChildCarouselViewHolder>() {
    private lateinit var dataItem: Results
    private lateinit var vhItemChildCarouselBinding: ItemImageViewHolderBinding

    override

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildCarouselViewHolder {
        vhItemChildCarouselBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_image_view_holder,
            parent,
            false
        )
        return ChildCarouselViewHolder(vhItemChildCarouselBinding, this)
    }

    override fun onBindViewHolder(holder: ChildCarouselViewHolder, position: Int) {
        dataItem = mainDataList[position]
        holder.bindToView(dataItem, position)
    }

    override fun getItemCount(): Int {
        return mainDataList.size
    }

    fun onChildItemClicked(view: View, data: Results) {
        val destination =
            MainScreenFragmentDirections.actionMainScreenFragmentToDetailsFragment(data)
        view.findNavController().navigate(destination)
    }

}

class ChildCarouselViewHolder(
    private val binding: ItemImageViewHolderBinding,
    private val adapter: ChildCarouselAdapter,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindToView(dataItem: Results, position: Int) {
        binding.itemdata = dataItem
        binding.clickListener = adapter
        binding.position = position
        binding.executePendingBindings()
    }
}
