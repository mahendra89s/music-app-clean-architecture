package com.example.musicapp.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentSongPlayerBinding
import com.example.musicapp.model.Music
import com.example.musicapp.utils.MediaPlayer
import com.example.musicapp.utils.setImageViewSrc
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SongPlayerFragment @Inject constructor() : Fragment() {


    @Inject
    lateinit var mediaPlayer: MediaPlayer
    lateinit var binding: FragmentSongPlayerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSongPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val music = arguments?.getParcelable<Music>("music")
        binding.data = music
        initializeSong(music?.uri!!)
    }

    private fun initializeSong(id: Uri) {
        mediaPlayer.initializeMediaPlayer(id)
        initializeSeekbar()
        binding.pausePlay.setOnClickListener {
            mediaPlayer.playAndPause()
            if(!mediaPlayer.isPlaying()){
                it.background = ContextCompat.getDrawable(it.context,R.drawable.ic_play)
            }else{
                it.background = ContextCompat.getDrawable(it.context,R.drawable.ic_pause)
            }
        }

        binding.musicSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) mediaPlayer.mediaSeekTo(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun initializeSeekbar() {
        binding.musicSeekbar.max = mediaPlayer.getDuration()
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    binding.musicSeekbar.progress = mediaPlayer.getCurrentPosition()
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    binding.musicSeekbar.progress = 0
                }
            }
        }, 0)
    }

}