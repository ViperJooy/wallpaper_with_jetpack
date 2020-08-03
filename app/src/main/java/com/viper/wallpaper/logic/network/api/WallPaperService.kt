package com.viper.wallpaper.logic.network.api

import com.viper.wallpaper.logic.model.WallPaperResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface WallPaperService {
    @POST("bz/getJson")
    fun getJson(@HeaderMap headers: Map<String, String>,@Body requestBody: RequestBody): Call<WallPaperResponse>
}