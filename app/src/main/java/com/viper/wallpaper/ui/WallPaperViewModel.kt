package com.viper.wallpaper.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.viper.wallpaper.logic.Repository
import com.viper.wallpaper.logic.model.Record
import com.viper.wallpaper.logic.model.RequestData

class WallPaperViewModel : ViewModel() {

    private val wallPaperLiveData = MutableLiveData<RequestData>()
    val wallPaperList = ArrayList<Record>()

    val getJsonLiveData = Transformations.switchMap(wallPaperLiveData) { requestData ->
        Repository.getJson(requestData)
    }

    fun getJson(requestData: RequestData) {
        wallPaperLiveData.value = requestData
    }
}