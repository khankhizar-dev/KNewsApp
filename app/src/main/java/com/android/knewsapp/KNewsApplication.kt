package com.android.knewsapp

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KNewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("KNewsApplication", "Application Started")
    }
}
