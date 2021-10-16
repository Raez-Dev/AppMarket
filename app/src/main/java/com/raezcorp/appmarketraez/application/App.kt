package com.raezcorp.appmarketraez.application

import android.app.Application
import com.raezcorp.appmarketraez.data.AppDatabase

class App : Application() {

    lateinit var instance : AppDatabase

    override fun onCreate() {
        super.onCreate()

        instance = AppDatabase.getInstance(this)
    }

}
