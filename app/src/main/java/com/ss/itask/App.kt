package com.ss.itask

import android.app.Application
import com.ss.itask.dao.Database

class App:Application() {

    companion object{
        lateinit var instance: App
        val database: Database by lazy {
            Database(instance)
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}