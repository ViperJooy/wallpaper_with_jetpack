package com.viper.wallpaper.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Created by viper.
 * Time: 20-7-22 10:42:11
 * Description:
 */
fun View.showSnackBar(text: String, actionText: String? = null, duration: Int = Snackbar.LENGTH_SHORT, block: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, text, duration)
    if (actionText.isNullOrEmpty() && block != null) {
        snackBar.setAction(actionText) {
            block()
        }
    }
    snackBar.show()
}

fun View.showSnackBar(resId: Int, actionResId: Int? = null, duration: Int = Snackbar.LENGTH_SHORT, block: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, resId, duration)
    if (actionResId != null && block != null) {
        snackBar.setAction(actionResId) {
            block()
        }
    }
    snackBar.show()
}