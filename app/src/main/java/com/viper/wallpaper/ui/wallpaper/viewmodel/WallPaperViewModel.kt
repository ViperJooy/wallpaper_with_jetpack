package com.viper.wallpaper.ui.wallpaper.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.*
import com.viper.wallpaper.logic.Repository
import com.viper.wallpaper.logic.model.RequestData
import com.viper.wallpaper.ui.wallpaper.adapter.WallPaperDataSource

class WallPaperViewModel @ViewModelInject constructor(
    private val repository: Repository
//    @Assisted val savedState: SavedStateHandle
) : ViewModel() {
    private val wallPaperLiveData = MutableLiveData<RequestData>()

//    val getJsonLiveData = Transformations.switchMap(wallPaperLiveData) { requestData ->
//        repository.getWallPaperListFlow(requestData)
//    }
//
//
//    fun getJson(requestData: RequestData) {
//        wallPaperLiveData.value = requestData
//    }

    val getWallPaperLiveData = Transformations.switchMap(wallPaperLiveData) { requestData ->
        Pager(PagingConfig(pageSize = 20)) {
            WallPaperDataSource(requestData)
        }.flow.cachedIn(viewModelScope).asLiveData()
    }

    fun getWallPaperFlow(requestData: RequestData) {
        wallPaperLiveData.value = requestData
    }


}