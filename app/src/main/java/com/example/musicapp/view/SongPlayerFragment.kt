package com.example.musicapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.musicapp.databinding.FragmentSongPlayerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongPlayerFragment : Fragment() {

   lateinit var binding: FragmentSongPlayerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSongPlayerBinding.inflate(inflater,container,false)
        return binding.root
    }

}