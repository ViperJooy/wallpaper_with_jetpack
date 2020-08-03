package com.viper.wallpaper

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.viper.wallpaper.ui.WallPaperViewModel
import okhttp3.MediaType
import okhttp3.RequestBody


class MainActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(WallPaperViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val map: MutableMap<String, Any?> = HashMap()
        map["target"] = "index"
        map["pageNum"] = 1
        val json = Gson().toJson(map)

        Log.d("TAG",json)

        val requestBody = RequestBody.create(
            MediaType.parse("Content-Type, application/json"),
            json
        )
        viewModel.getJson(requestBody)

        viewModel.getJsonLiveData.observe(this, Observer { result ->
            val places = result.getOrNull()
            if (places != null) {
                viewModel.wallPaperList.clear()
                viewModel.wallPaperList.addAll(places)
                Log.d("qqq", "result {$viewModel.wallPaperList.size}")
//                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "未获取到任何数据", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}