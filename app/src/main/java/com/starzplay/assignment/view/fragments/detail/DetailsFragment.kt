package com.starzplay.assignment.view.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.starzplay.assignment.R
import com.starzplay.assignment.databinding.FragmentDetailsBinding
import com.starzplay.networking.models.Results


class DetailsFragment : Fragment() {

    private lateinit var navController: NavController
    private val arg: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (::binding.isInitialized) {
            binding.itemdata = arg.itemData
            binding.clickListener = this
            binding.executePendingBindings()
        }
        navController = view.findNavController()
    }

    fun onPlayButtonClicked(data: Results) {
        val destination =
            DetailsFragmentDirections.actionDetailsFragmentToPlayerScreenFragment(data)
        navController.navigate(destination)
    }
}