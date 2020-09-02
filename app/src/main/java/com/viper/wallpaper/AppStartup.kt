package com.viper.wallpaper

import android.content.Context
import com.drake.statelayout.StateConfig
import com.rousetime.android_startup.AndroidStartup
import com.viper.wallpaper.utils.Constants
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

/**
 * Created by viper.
 * Time: 20-8-25 16:26:40
 * Description:
 */
class AppStartup : AndroidStartup<Boolean>() {
    override fun callCreateOnMainThread(): Boolean = true

    override fun create(context: Context): Boolean? {
        RetrofitUrlManager.getInstance().setDebug(true)
        //将每个 BaseUrl 进行初始化,运行时可以随时改变 DOMAIN_NAME 对应的值,从而达到切换 BaseUrl 的效果
        RetrofitUrlManager.getInstance()
            .putDomain(Constants.WALLPAPER_DOMAIN_NAME, Constants.WALLPAPER_API_URL)
        RetrofitUrlManager.getInstance()
            .putDomain(Constants.DOUYU_DOMAIN_NAME, Constants.DOUYU_API_URL)
        StateConfig.apply {
            emptyLayout = R.layout.layout_empty
            errorLayout = R.layout.layout_error
            loadingLayout = R.layout.layout_loading

            setRetryIds(R.id.msg)
        }

        return true
    }

    override fun waitOnMainThread(): Boolean = false
}