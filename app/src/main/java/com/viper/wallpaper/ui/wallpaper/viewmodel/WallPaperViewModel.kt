package com.viper.wallpaper.ui.wallpaper.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.viper.wallpaper.logic.Repository
import com.viper.wallpaper.logic.model.Record
import com.viper.wallpaper.logic.model.RequestData
import com.viper.wallpaper.ui.wallpaper.adapter.WallPaperDataSource
import dagger.Provides
import javax.inject.Singleton

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


    val getFlowLiveData = Transformations.switchMap(wallPaperLiveData) { requestData ->
        Pager(PagingConfig(pageSize = 20)) {
            WallPaperDataSource(requestData)
        }.flow.cachedIn(viewModelScope).asLiveData()
    }

    fun getFlow(requestData: RequestData) {
        wallPaperLiveData.value = requestData
    }


}