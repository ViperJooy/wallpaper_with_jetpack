package com.viper.wallpaper.logic.network.api

import com.viper.wallpaper.logic.model.RequestData
import com.viper.wallpaper.logic.model.WallPaperResponse
import com.viper.wallpaper.utils.Constants
import me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST

interface WallPaperService {
    @Headers(DOMAIN_NAME_HEADER + Constants.WALLPAPER_DOMAIN_NAME)
    @POST("bz/getJson")
    fun getJson(
        @HeaderMap headers: Map<String, String>,
        @Body requestData: RequestData
    ): Call<WallPaperResponse>
}