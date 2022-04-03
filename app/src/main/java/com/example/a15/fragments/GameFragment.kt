package com.example.a15.fragments

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.a15.MainActivity
import com.example.a15.PuzzleBoardView
import com.example.a15.R
import com.example.a15.databinding.FragmentGameBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainActivity: MainActivity = activity as MainActivity
        val clickSound : MediaPlayer = MediaPlayer.create(context, R.raw.click)
        val puzzleBoardView = context?.let { PuzzleBoardView(it, mainActivity.n) }

        binding.gameContainer.addView(puzzleBoardView)

        binding.buttonNewGame.setOnClickListener {
            if (puzzleBoardView != null) {
                soundClick(clickSound)
                puzzleBoardView.initGame()
            }
            if (puzzleBoardView != null) {
                soundClick(clickSound)
                puzzleBoardView.invalidate()
            }
        }
        binding.buttonBack.setOnClickListener {
            soundClick(clickSound)
            (context as MainActivity)
                .fragmentManager
                .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        }
    }

    fun soundClick (sound : MediaPlayer) {
        CoroutineScope(Dispatchers.IO).launch {  sound.start() }

    }

}
