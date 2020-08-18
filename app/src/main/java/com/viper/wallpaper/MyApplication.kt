package com.viper.wallpaper

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by viper.
 * Time: 20-8-14 17:40:45
 * Description:
 */
@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}