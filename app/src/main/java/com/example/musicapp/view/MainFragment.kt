package com.example.musicapp.view

import android.content.ContentUris
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.R
import com.example.musicapp.adapter.SongListAdapter
import com.example.musicapp.databinding.FragmentMainBinding
import com.example.musicapp.model.Music
import com.example.musicapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), SongListAdapter.ClickListener {
    lateinit var binding: FragmentMainBinding
    val viewmodel by viewModels<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewmodel.getSongs().observe(viewLifecycleOwner){ it ->
            Log.e("rv---","$it")
            binding.rvSongs.apply {
                adapter = SongListAdapter(it,requireContext(),this@MainFragment)
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            }
        }
        //Add Permission Code
    }

    override fun cLick(music : Music) {
        findNavController().navigate(R.id.action_mainFragment_to_songPlayerFragment, bundleOf("music" to music))
    }


}