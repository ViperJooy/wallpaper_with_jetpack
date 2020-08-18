package com.viper.wallpaper.ui.wallpaper.adapter

import android.util.Log
import androidx.paging.PagingSource
import com.viper.wallpaper.logic.model.Record
import com.viper.wallpaper.logic.model.RequestData
import com.viper.wallpaper.logic.network.Network

/**
 * Created by viper.
 * Time: 20-8-18 11:46:36
 * Description:
 */
class WallPaperDataSource(private val requestData: RequestData) : PagingSource<Int, Record>() {

    //添加请求头验证
    private val map = mutableMapOf(
        "access" to "36a1b1711b3978f92ad4bf5f405f43d26cdd0a037eddfdc233d7674b9462e802",
        "location" to "bz.zzzmh.cn",
        "sign" to "50f9a39a0de9dc692baddf4d9ece3582",
        "timestamp" to "1596463830570",
        "Content-Type" to "application/json"
    )

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Record> {
        return try {
            val page = params.key ?: 1
            Log.d("page", "current_page: $page")
            val requestData = RequestData(requestData.target, page)
            //获取网络数据
            val result = Network.getJson(map, requestData)
            LoadResult.Page(
                //需要加载的数据
                data = result.result.records,
                //如果可以往上加载更多就设置该参数，否则不设置
                prevKey = null,
                //加载下一页的key 如果传null就说明到底了
                nextKey = if (result.result.current == result.result.pages) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}