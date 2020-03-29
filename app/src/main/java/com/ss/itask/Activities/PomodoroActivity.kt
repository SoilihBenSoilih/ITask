package com.ss.itask.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import com.ss.itask.R

class PomodoroActivity : AppCompatActivity() {

    lateinit  var roundingAlone: Animation
    lateinit  var icAnchor: ImageView
    lateinit var buttonStart: Button
    lateinit var buttonStop: Button
    lateinit var timer: Chronometer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro)

        buttonStart = findViewById(R.id.buttonStart)
        buttonStop = findViewById(R.id.buttonStop)
        icAnchor = findViewById(R.id.icanchor)
        timer = findViewById(R.id.timeHer)

        //create an optional animation
        buttonStop.alpha = 0f



        //loading animations
        roundingAlone = AnimationUtils.loadAnimation(this,R.anim.roundingalone)

        buttonStart.setOnClickListener {
            icAnchor.startAnimation(roundingAlone)
            buttonStop.animate().alpha(1f).translationYBy(-120f).setDuration(1000).start()
            buttonStart.animate().alpha(0f).translationY(120f).setDuration(1000).start()
            timer.base = SystemClock.elapsedRealtime()
            timer.start()
        }

        buttonStop.setOnClickListener {
            buttonStart.animate().alpha(1f).translationYBy(-120f).setDuration(1000).start()
            buttonStop.animate().alpha(0f).translationYBy(120f).setDuration(1000).start()
        }
    }
}
