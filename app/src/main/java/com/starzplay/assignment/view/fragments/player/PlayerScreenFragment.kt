package com.starzplay.assignment.view.fragments.player

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.starzplay.assignment.R
import com.starzplay.assignment.databinding.FragmentPlayerScreenBinding


class PlayerScreenFragment : Fragment() {
    var videoUrl =
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
    private val arg: PlayerScreenFragmentArgs by navArgs()
    private lateinit var binding: FragmentPlayerScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_player_screen, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toggleVideoMode(imageView = true, playButton = true)
        if (::binding.isInitialized) {
            binding.itemdata = arg.itemData
            binding.clickListener = this
            binding.executePendingBindings()
        }
    }

    fun playVideo() {
        toggleVideoMode(videoView = true)
        binding.videoView.apply {
            val uri: Uri = Uri.parse(videoUrl)
            setVideoURI(uri)
            val mediaController = MediaController(requireContext()).also {
                it.setAnchorView(this)
                it.setMediaPlayer(this)
            }
            setMediaController(mediaController)
            start()
        }
    }


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onDestroy() {
        super.onDestroy()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun toggleVideoMode(
        videoView: Boolean = false,
        imageView: Boolean = false,
        playButton: Boolean = false
    ) {
        if (::binding.isInitialized) {
            binding.videoView.visibility = if (videoView) View.VISIBLE else View.GONE
            binding.fullImage.visibility = if (imageView) View.VISIBLE else View.GONE
            binding.playButton.visibility = if (playButton) View.VISIBLE else View.GONE
        }
    }
}