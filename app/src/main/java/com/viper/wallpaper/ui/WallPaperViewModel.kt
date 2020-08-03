package com.viper.wallpaper.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.viper.wallpaper.logic.Repository
import com.viper.wallpaper.logic.model.Record
import okhttp3.RequestBody
import kotlin.math.log

class WallPaperViewModel : ViewModel() {

    private val wallPaperLiveData = MutableLiveData<RequestBody>()
    val wallPaperList = ArrayList<Record>()

    val getJsonLiveData = Transformations.switchMap(wallPaperLiveData) { requestBody ->
        Repository.getJson(requestBody)
    }

    fun getJson(requestBody: RequestBody) {
        wallPaperLiveData.value = requestBody
    }
}