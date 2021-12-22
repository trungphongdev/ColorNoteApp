package com.example.mynoteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextWatcher
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
private const val TIME_COUNT = 2000L
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        anim()

    }
    private fun anim() {
        val anim: Animation = AnimationUtils.loadAnimation(this@MainActivity,R.anim.logo_app)
        imgLogo.startAnimation(anim)
        txv_nameApp.startAnimation(anim)
        object: CountDownTimer(TIME_COUNT,1000) {
            override fun onTick(p0: Long) {
            }
            override fun onFinish() {
                startActivity(Intent(this@MainActivity,MainActivity2::class.java))
            }

        }.start()
    }

    override fun onRestart() {
        super.onRestart()
        anim()
    }
}