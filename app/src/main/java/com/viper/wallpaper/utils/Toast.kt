package com.viper.wallpaper.utils

import android.content.Context
import android.widget.TextView
import android.widget.Toast

/**
 * Created by viper.
 * Time: 20-7-22 10:42:11
 * Description:
 */
fun String.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}

fun Int.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}