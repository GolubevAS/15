package com.example.a15

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {


    private val SPLASH_TIME_OUT = 3000
    lateinit var topAnimation: Animation
    lateinit var one : View
    lateinit var two : View
    lateinit var three : View
    lateinit var four : View
    lateinit var five : View
    lateinit var six : View
    lateinit var seven : View
    lateinit var eight : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)

        one = findViewById(R.id.one_line)
        two = findViewById(R.id.two_line)
        three = findViewById(R.id.three_line)
        four = findViewById(R.id.four_line)
        five = findViewById(R.id.five_line)
        six = findViewById(R.id.six_line)
        seven = findViewById(R.id.seven_line)
        eight = findViewById(R.id.eight_line)

        one.animation = topAnimation
        two.animation = topAnimation
        three.animation = topAnimation
        four.animation = topAnimation
        five.animation = topAnimation
        six.animation = topAnimation
        seven.animation = topAnimation
        eight.animation = topAnimation

        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT.toLong())

    }




}