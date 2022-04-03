package com.example.a15

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.a15.fragments.GameFragment
import com.example.a15.fragments.StartFragment

class MainActivity : AppCompatActivity() {


    val fragmentManager: FragmentManager = supportFragmentManager
    var n = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        showStartFragment()
    }

    private fun showStartFragment() {
        val fragment = StartFragment()
        fragmentManager.apply {
            beginTransaction()
                .replace(R.id.fragment_holder, fragment)
                .commit()
        }
    }

    fun showGameFragment(newN: Int) {
        n = newN
        val fragment = GameFragment()

        fragmentManager.apply {
            beginTransaction()
                .replace(R.id.fragment_holder, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
