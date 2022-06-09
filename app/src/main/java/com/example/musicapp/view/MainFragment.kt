package com.example.musicapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
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
            binding.rvSongs.apply {
                adapter = SongListAdapter(it,requireContext(),this@MainFragment)
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            }
        }
        //Add Permission Code
    }

    override fun cLick(id : Music) {
        childFragmentManager.commit {
            val b = Bundle()
            b.putParcelable("music",id)
            val fragment = SongPlayerFragment()
            fragment.arguments = b
            replace(R.id.child_container,fragment)
            addToBackStack(null)
        }
    }

}