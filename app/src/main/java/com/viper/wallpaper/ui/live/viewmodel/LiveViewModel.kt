package com.viper.wallpaper.ui.live.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.viper.wallpaper.logic.Repository
import com.viper.wallpaper.logic.model.RequestData

class LiveViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val cateLiveData = MutableLiveData<RequestData>()

    val getCateLiveFlowData = Transformations.switchMap(cateLiveData) {
        repository.getCateListFlow()
    }

    fun getLive(requestData: RequestData) {
        cateLiveData.value = requestData
    }


}