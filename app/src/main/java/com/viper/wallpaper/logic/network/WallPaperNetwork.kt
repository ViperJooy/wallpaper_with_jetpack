package com.viper.wallpaper.logic.network


import com.viper.wallpaper.logic.model.RequestData
import com.viper.wallpaper.logic.network.api.WallPaperService
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object WallPaperNetwork {
    private val wallpaperService = ServiceCreator.create(WallPaperService::class.java)

    suspend fun getJson(headers: Map<String, String>, requestData: RequestData) =
        wallpaperService.getJson(headers, requestData).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}