package com.viper.wallpaper.logic.network;

import com.viper.wallpaper.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object ServiceCreator {


    /**
     * @Provides 常用于被 @Module 注解标记类的内部的方法，并提供依赖项对象。
     * @Singleton 提供单例
     */
    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient = RetrofitUrlManager.getInstance()
        .with(OkHttpClient.Builder())
        .readTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.WALLPAPER_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient())
        .build()

    @ExperimentalCoroutinesApi
    fun <T> create(serviceClass: Class<T>): T = retrofit().create(serviceClass)

    @ExperimentalCoroutinesApi
    inline fun <reified T> create(): T = create(T::class.java)
}
