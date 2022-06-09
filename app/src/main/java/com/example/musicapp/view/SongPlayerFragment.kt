package com.example.musicapp.view

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentSongPlayerBinding
import com.example.musicapp.model.Music
import com.example.musicapp.model.SongFragmentMotionState
import com.example.musicapp.utils.*
import com.example.musicapp.viewmodel.SongPlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SongPlayerFragment @Inject constructor() : Fragment(){


    @Inject
    lateinit var mediaPlayer: MediaPlayer

    @Inject
    lateinit var timer: Timer

    @Inject lateinit var notification:Notification
    lateinit var binding: FragmentSongPlayerBinding
    lateinit var paramsViewGroup: FrameLayout.LayoutParams

    val viewmodel by viewModels<SongPlayerViewModel>()

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



        binding.pausePlay.setOnTouchListener { _view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    viewmodel.onPlayPauseButtonClick(_view)
                }
            }
            true
        }

        timer.invoke(mediaPlayer.getDuration().toLong())

        viewmodel.timerEvent.observe(viewLifecycleOwner) {
            lifecycleScope.launchWhenStarted {
                when (it) {
                    PlayPauseEvent.Play -> {
                        onPlay(music)
                        binding.pausePlay.background =
                            ContextCompat.getDrawable(requireContext(), R.drawable.ic_pause)
                        timer.startTime()
                        timer.timer.collect { time ->
                            binding.musicSeekbar.progress = mediaPlayer.getCurrentPosition()
                            binding.timer.setTime(time)
                        }
                    }
                    PlayPauseEvent.Pause -> {
                        Log.e("pause", "pause")
                        binding.pausePlay.background =
                            ContextCompat.getDrawable(requireContext(), R.drawable.ic_play)
                        timer.pauseTime()
                    }
                    PlayPauseEvent.Stop -> {
                        timer.stopTimer()
                    }
                    else -> {
                    }

                }
            }

        }

        lifecycleScope.launchWhenStarted {
            motionState.collect {
                motionLayoutListener(it)
            }
        }

    }


    private fun initializeSong(id: Uri) {

        mediaPlayer.initializeMediaPlayer(id)

        binding.musicSeekbar.max = mediaPlayer.getDuration()
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

    override fun onStop() {
        super.onStop()
        Log.e("hello","stop")
        timer.stopTimer()
    }

    private fun onPlay(music: Music){
        notification.invoke(music,R.id.pause_play,0,1)

    }

}