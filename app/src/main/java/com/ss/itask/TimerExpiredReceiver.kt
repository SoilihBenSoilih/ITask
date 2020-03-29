package com.ss.itask

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.resocoder.timertutorial.util.PrefUtil
import com.ss.itask.Activities.TimerActivity
import com.ss.itask.util.NotificationUtil

class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtil.showTimerExpired(context)

        PrefUtil.setTimerState(TimerActivity.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}
