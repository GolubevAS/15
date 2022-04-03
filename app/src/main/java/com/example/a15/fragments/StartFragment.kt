package com.example.a15.fragments

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a15.MainActivity
import com.example.a15.R
import com.example.a15.databinding.FragmentStartBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainActivity: MainActivity = activity as MainActivity
        val clickSound : MediaPlayer = MediaPlayer.create(context, R.raw.click)

        binding.but3X3.setOnClickListener {
            soundClick(clickSound)
            mainActivity.showGameFragment(3)
        }
        binding.but4X4.setOnClickListener {
            soundClick(clickSound)
            mainActivity.showGameFragment(4)
        }
        binding.but5X5.setOnClickListener {
            soundClick(clickSound)
            mainActivity.showGameFragment(5)
        }
        binding.but6X6.setOnClickListener {
            soundClick(clickSound)
            mainActivity.showGameFragment(6)
        }
        binding.but7X7.setOnClickListener {
            soundClick(clickSound)
            mainActivity.showGameFragment(7)
        }
        binding.but8X8.setOnClickListener {
            soundClick(clickSound)
            mainActivity.showGameFragment(8)
        }
        binding.but9X9.setOnClickListener {
            soundClick(clickSound)
            mainActivity.showGameFragment(9)
        }
        binding.but10X10.setOnClickListener {
            soundClick(clickSound)
            mainActivity.showGameFragment(10)
        }
    }

    fun soundClick (sound : MediaPlayer) {
        CoroutineScope(Dispatchers.IO).launch {  sound.start() }
    }

}
