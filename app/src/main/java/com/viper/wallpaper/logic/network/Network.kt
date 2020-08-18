package com.viper.wallpaper.logic.network


import com.viper.wallpaper.logic.model.RequestData
import com.viper.wallpaper.logic.network.api.WallPaperService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Module
@InstallIn(ActivityComponent::class)
object Network {

    private val wallpaperService = ServiceCreator.create(WallPaperService::class.java)

    @Provides
    @Singleton
    suspend fun getJson(headers: Map<String, String>, requestData: RequestData) =
        wallpaperService.getJson(headers, requestData).await()

    @ExperimentalCoroutinesApi
    suspend fun <T> Call<T>.await(): T {
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