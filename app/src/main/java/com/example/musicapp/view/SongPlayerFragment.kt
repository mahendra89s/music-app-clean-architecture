package com.example.musicapp.view

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentSongPlayerBinding
import com.example.musicapp.model.Music
import com.example.musicapp.model.SongFragmentMotionState
import com.example.musicapp.utils.MediaPlayer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SongPlayerFragment @Inject constructor() : Fragment() {


    @Inject
    lateinit var mediaPlayer: MediaPlayer
    lateinit var binding: FragmentSongPlayerBinding
    lateinit var paramsViewGroup: FrameLayout.LayoutParams

    var motionFlag = false

    private var motionState = MutableStateFlow(SongFragmentMotionState.EXPANDED)


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
        paramsViewGroup = binding.motionLayout.layoutParams as FrameLayout.LayoutParams
        initializeSong(music?.uri!!)


        lifecycleScope.launchWhenStarted {
            motionState.collect {
                motionLayoutListener(it)
            }
        }
    }

    private fun initializeSong(id: Uri) {
        mediaPlayer.initializeMediaPlayer(id)
        initializeSeekbar()
        binding.pausePlay.setOnClickListener {
            mediaPlayer.playAndPause()
            if (!mediaPlayer.isPlaying()) {
                it.background = ContextCompat.getDrawable(it.context, R.drawable.ic_play)
            } else {
                it.background = ContextCompat.getDrawable(it.context, R.drawable.ic_pause)
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

    private suspend fun motionLayoutListener(motion: SongFragmentMotionState) {

        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {

            override fun onTransitionStarted(p0: MotionLayout?, startId: Int, endId: Int) {

                if (motion == SongFragmentMotionState.COLLAPSED) {
                    paramsViewGroup = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        Gravity.TOP
                    )
                    binding.motionLayout.layoutParams = paramsViewGroup
                }
            }

            override fun onTransitionChange(
                p0: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) {
                if (motion == SongFragmentMotionState.EXPANDED) {
                    paramsViewGroup = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        500,
                        Gravity.BOTTOM
                    )
                    binding.motionLayout.layoutParams = paramsViewGroup
                }
                lifecycleScope.launch {
                    switchMotionState(motion)
                }

            }

            override fun onTransitionTrigger(
                p0: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }
        })


    }

    private suspend fun switchMotionState(state: SongFragmentMotionState) {
        when (state) {
            SongFragmentMotionState.COLLAPSED -> motionState.emit(SongFragmentMotionState.EXPANDED)
            SongFragmentMotionState.EXPANDED -> motionState.emit(SongFragmentMotionState.COLLAPSED)
        }
    }


}