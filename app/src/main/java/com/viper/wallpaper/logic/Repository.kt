package com.viper.wallpaper.logic

import androidx.lifecycle.liveData
import com.viper.wallpaper.logic.model.RequestData
import com.viper.wallpaper.logic.network.WallPaperNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object Repository {
    fun getJson(requestData: RequestData) = fire(Dispatchers.IO) {

        //添加请求头验证
        val map = mutableMapOf(
            "access" to "36a1b1711b3978f92ad4bf5f405f43d26cdd0a037eddfdc233d7674b9462e802",
            "location" to "bz.zzzmh.cn",
            "sign" to "50f9a39a0de9dc692baddf4d9ece3582",
            "timestamp" to "1596463830570",
            "Content-Type" to "application/json"
        )

        val wallPaperResponse = WallPaperNetwork.getJson(map, requestData)
        if (wallPaperResponse.msg == "success") {
            val record = wallPaperResponse.result.records
            Result.success(record)
        } else {
            Result.failure(RuntimeException("response msg is ${wallPaperResponse.msg}"))
        }

    }


    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}