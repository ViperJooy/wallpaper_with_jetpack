package com.viper.wallpaper.logic

import androidx.lifecycle.liveData
import com.viper.wallpaper.logic.model.RequestData
import com.viper.wallpaper.logic.network.Network
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(ActivityComponent::class)
class Repository @Inject constructor() {

    //添加请求头验证
    private val map = mutableMapOf(
        "access" to "36a1b1711b3978f92ad4bf5f405f43d26cdd0a037eddfdc233d7674b9462e802",
        "location" to "bz.zzzmh.cn",
        "sign" to "50f9a39a0de9dc692baddf4d9ece3582",
        "timestamp" to "1596463830570",
        "Content-Type" to "application/json"
    )

    @Provides
    @Singleton
    fun getJson(requestData: RequestData) = fire(Dispatchers.IO) {
        val wallPaperResponse = Network.getJson(map, requestData)
        if (wallPaperResponse.msg == "success") {
            val record = wallPaperResponse.result.records
            Result.success(record)
        } else {
            Result.failure(RuntimeException("response msg is ${wallPaperResponse.msg}"))
        }

    }

    @Provides
    @Singleton
    fun getFlow(requestData: RequestData) = fire(Dispatchers.IO) {
        val wallPaperResponse = Network.getFlow(map, requestData)
        if (wallPaperResponse.msg == "success") {
            val record = wallPaperResponse.result.records
            Result.success(record)
        } else {
            Result.failure(RuntimeException("response msg is ${wallPaperResponse.msg}"))
        }

    }

    @ExperimentalCoroutinesApi
    fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}