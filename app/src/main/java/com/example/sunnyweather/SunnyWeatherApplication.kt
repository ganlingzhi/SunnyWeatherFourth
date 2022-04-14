package com.example.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        @SuppressLint("StaticFieldLeak")
        lateinit var application: SunnyWeatherApplication
        const val TOKEN = "gTTTa0dhnJvj747L"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        application = this
    }
}