package com.viper.wallpaper

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.viper.wallpaper.utils.Constants
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

class MyContentProvider : ContentProvider() {

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun onCreate(): Boolean {
        RetrofitUrlManager.getInstance().setDebug(true)
        //将每个 BaseUrl 进行初始化,运行时可以随时改变 DOMAIN_NAME 对应的值,从而达到切换 BaseUrl 的效果
        RetrofitUrlManager.getInstance().putDomain(Constants.WALLPAPER_DOMAIN_NAME, Constants.WALLPAPER_API_URL)
        RetrofitUrlManager.getInstance().putDomain(Constants.DOUYU_DOMAIN_NAME, Constants.DOUYU_API_URL)
//        RetrofitUrlManager.getInstance().putDomain(Constants.VIDEO_DOMAIN_NAME, Constants.VIDEO_API_URL)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        TODO("Implement this to handle query requests from clients.")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}
