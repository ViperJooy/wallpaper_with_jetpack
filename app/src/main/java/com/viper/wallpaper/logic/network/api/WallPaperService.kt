package com.viper.wallpaper.logic.network.api

import com.viper.wallpaper.logic.model.RequestData
import com.viper.wallpaper.logic.model.WallPaperResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface WallPaperService {
    @POST("bz/getJson")
    fun getJson(@HeaderMap headers:Map<String,String>,@Body requestData: RequestData): Call<WallPaperResponse>
}