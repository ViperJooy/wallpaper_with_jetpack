package com.viper.wallpaper.logic.network.api

import com.viper.wallpaper.logic.model.RequestData
import com.viper.wallpaper.logic.model.live.douyu.CategoryResponse
import com.viper.wallpaper.logic.model.wallpaper.WallPaperResponse
import com.viper.wallpaper.utils.Constants
import me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers(DOMAIN_NAME_HEADER + Constants.WALLPAPER_DOMAIN_NAME)
    @POST("bz/getJson")
    fun getWallPaperList(
        @HeaderMap headers: Map<String, String>,
        @Body requestData: RequestData
    ): Call<WallPaperResponse>


    @Headers(DOMAIN_NAME_HEADER + Constants.DOUYU_DOMAIN_NAME)
    @GET("api/cate/list")
    fun getCateList(): Call<CategoryResponse>
}