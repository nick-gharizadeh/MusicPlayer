package com.example.musicplayer.ui.homefragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.musicplayer.BuildConfig
import com.example.musicplayer.databinding.FragmentHomeBinding
import com.example.musicplayer.ui.adapter.MusicAdapter


class HomeFragment : Fragment() {
    val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        getAccessibility()
        val musicAdapter = MusicAdapter()
        binding.musicsRecyclerView.adapter = musicAdapter
        musicAdapter.submitList(homeViewModel.musicsList)

    }


    @RequiresApi(Build.VERSION_CODES.R)
    fun getAccessibility() {
        val uri: Uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID)
        startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, uri))
    }
}