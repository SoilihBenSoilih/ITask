package com.ss.itask.Activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.resocoder.timertutorial.util.PrefUtil
import com.ss.itask.App
import com.ss.itask.Model.Project
import com.ss.itask.Model.Task
import com.ss.itask.R
import com.ss.itask.TimerExpiredReceiver
import com.ss.itask.util.NotificationUtil
import kotlinx.android.synthetic.main.activity_timer.*
import kotlinx.android.synthetic.main.content_timer.*
import java.util.*

class TimerActivity : AppCompatActivity() {
    lateinit  var roundingAlone: Animation
    lateinit  var icAnchor: ImageView
    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 0
    private var timerState = TimerState.Stopped
    private var secondsRemaining: Long = 0
    lateinit var sensorManager: SensorManager
    private var acelVal:Float = 0.0f
    private var acelLast:Float = 0.0f
    private var shake:Float = 0.0f
    lateinit var task: Task
    private var saved = false

    enum class TimerState{
        Stopped, Paused, Running
    }


    companion object {
        fun setAlarm(context: Context, nowSeconds: Long, secondsRemaining: Long): Long{
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            }
            PrefUtil.setAlarmSetTime(nowSeconds, context)
            return wakeUpTime
        }

        fun removeAlarm(context: Context){
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            PrefUtil.setAlarmSetTime(0, context)
        }

        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        icAnchor = findViewById(R.id.icanchor)
        roundingAlone = AnimationUtils.loadAnimation(this,R.anim.roundingalone)
        saved = false
//        setSupportActionBar(toolbar as Tgoolbar)
//        supportActionBar?.setIcon(R.drawable.ic_timer)
//        supportActionBar?.title = "      Timer"

        /*
            Gestion du capteur accelerometre
         */

        acelVal = SensorManager.GRAVITY_EARTH
        acelLast = SensorManager.GRAVITY_EARTH
        shake = 0.00f

        sensorManager= getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(sensorListener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL)


     /*
     * Timer managment Start, Pause, Stop
     * */

        fab_start.setOnClickListener{
            startTimer()
            timerState =  TimerState.Running
            updateButtons()
            if(!saved){
                task.duration = task.duration + PrefUtil.getTimerLength(this)
                task.status= 1
                saved =true
                task.date = getDate()
                println(getDate())
                App.database.updateTask(task)
            }
        }
        fab_pause.setOnClickListener {
            timer.cancel()
            timerState = TimerState.Paused
            icAnchor.clearAnimation()
            updateButtons()
        }
        fab_stop.setOnClickListener {
            timer.cancel()
            onTimerFinished()
        }

        //gestion de la tache
        task = intent.getParcelableExtra<Task>("tache")
        task_name_in_pomodoro.text = task.name
        Toast.makeText(this, "Tache: ${task.name}", Toast.LENGTH_SHORT).show()

    }

     private var sensorListener: SensorEventListener = object:SensorEventListener{
         override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
         }

         override fun onSensorChanged(event: SensorEvent?) {
             var x = event?.values?.get(0)
             var y = event?.values?.get(1)
             var z = event?.values?.get(2)

             acelLast = acelVal
             var tmp: Double = (x!!.times(x)).toDouble() + (y!!.times(y)).toDouble() + (z!!.times(z)).toDouble()
             acelVal = Math.sqrt(tmp).toFloat()
             var delta:Float = acelVal-acelLast
             shake = shake*0.9f + delta
             if (shake>24){
                 if (timerState == TimerState.Paused){
                     startTimer()
                     timerState =  TimerState.Running
                     updateButtons()
                 }else if (timerState == TimerState.Running){
                     timer.cancel()
                     timerState = TimerState.Paused
                     icAnchor.clearAnimation()
                     updateButtons()
                 }else{
                     startTimer()
                     timerState =  TimerState.Running
                     updateButtons()
                 }
             }
         }

     }


    override fun onResume() {
        super.onResume()
        initTimer()
        removeAlarm(this)
        NotificationUtil.hideTimerNotification(this)
    }

    override fun onPause() {
        super.onPause()
        if (timerState == TimerState.Running){
            timer.cancel()
            val wakeUpTime = setAlarm(this, nowSeconds, secondsRemaining)
            NotificationUtil.showTimerRunning(this, wakeUpTime)
        }
        else if (timerState == TimerState.Paused){
            NotificationUtil.showTimerPaused(this)
        }

        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, this)
        PrefUtil.setSecondsRemaining(secondsRemaining, this)
        PrefUtil.setTimerState(timerState, this)
    }

    private fun initTimer(){
        timerState = PrefUtil.getTimerState(this)

        //we don't want to change the length of the timer which is already running
        //if the length was changed in settings while it was backgrounded
        if (timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLength()

        secondsRemaining = if (timerState == TimerState.Running || timerState == TimerState.Paused)
            PrefUtil.getSecondsRemaining(this)
        else
            timerLengthSeconds

        val alarmSetTime = PrefUtil.getAlarmSetTime(this)
        if (alarmSetTime > 0)
            secondsRemaining -= nowSeconds - alarmSetTime

        if (secondsRemaining <= 0)
            onTimerFinished()
        else if (timerState == TimerState.Running)
            startTimer()

        updateButtons()
        updateCountdownUI()
    }

    private fun onTimerFinished(){
        timerState = TimerState.Stopped
        //set the length of the timer to be the one set in SettingsActivity
        //if the length was changed when the timer was running
        setNewTimerLength()
        icAnchor.clearAnimation()
        progress_countdown.progress = 0

        PrefUtil.setSecondsRemaining(timerLengthSeconds, this)
        secondsRemaining = timerLengthSeconds

        updateButtons()
        updateCountdownUI()
    }

    private fun startTimer(){
        timerState = TimerState.Running
        icAnchor.startAnimation(roundingAlone)
        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                updateCountdownUI()
            }
        }.start()

    }

    private fun setNewTimerLength(){
        val lengthInMinutes = PrefUtil.getTimerLength(this)
        timerLengthSeconds = (lengthInMinutes * 60L)
        progress_countdown.max = timerLengthSeconds.toInt()
    }

    private fun setPreviousTimerLength(){
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(this)
        progress_countdown.max = timerLengthSeconds.toInt()
    }

    private fun updateCountdownUI(){
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        textView_countdown.text = "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
        progress_countdown.progress = (timerLengthSeconds - secondsRemaining).toInt()
    }

    private fun updateButtons(){
        when (timerState) {
            TimerState.Running ->{
                fab_start.isEnabled = false
                fab_pause.isEnabled = true
                fab_stop.isEnabled = true
            }
            TimerState.Stopped -> {
                fab_start.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = false
            }
            TimerState.Paused -> {
                fab_start.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = true
            }
        }
    }

    private fun getDate():String{
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var dayString=""
        var monthString=""
        if(day<10){
            dayString = "0$day"
        }else{
            dayString = "$day"
        }
        if (month<10){
            monthString = "0$month"
        }else{
            monthString = "0$month"
        }
        val date = "${dayString}/${monthString}/${year}"
        return date
    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_timer, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> {
//                val intent = Intent(this, SettingsActivity::class.java)
//                startActivity(intent)
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}
