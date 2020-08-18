package com.viper.wallpaper.ui.wallpaper.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.viper.wallpaper.logic.Repository
import com.viper.wallpaper.logic.model.Record
import com.viper.wallpaper.logic.model.RequestData

class WallPaperViewModel @ViewModelInject constructor(
    private val repository: Repository
//    @Assisted val savedState: SavedStateHandle
) : ViewModel() {
    private val wallPaperLiveData = MutableLiveData<RequestData>()
    val wallPaperList = ArrayList<Record>()

    val getJsonLiveData = Transformations.switchMap(wallPaperLiveData) { requestData ->
        repository.getJson(requestData)
    }

    fun getJson(requestData: RequestData) {
        wallPaperLiveData.value = requestData
    }
}