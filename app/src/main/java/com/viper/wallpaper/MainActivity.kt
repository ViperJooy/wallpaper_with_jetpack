package com.viper.wallpaper

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.viper.wallpaper.logic.model.RequestData
import com.viper.wallpaper.ui.WallPaperAdapter
import com.viper.wallpaper.ui.WallPaperViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val DEFAULT_SPAN = 2
        private const val SCREEN_WIDTH_WITH_DEFAULT_SPAN = 1200
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(WallPaperViewModel::class.java)
    }
    private lateinit var wallPaperAdapter: WallPaperAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val requestData = RequestData("anime", 1)
        viewModel.getJson(requestData)
        wallPaperAdapter = WallPaperAdapter(this, viewModel.wallPaperList)

        rvWallpaper.apply {
            layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
            adapter = wallPaperAdapter
        }

        viewModel.getJsonLiveData.observe(this, Observer { result ->
            val places = result.getOrNull()
            if (places != null) {
                viewModel.wallPaperList.clear()
                viewModel.wallPaperList.addAll(places)
                wallPaperAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "未获取到任何数据", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

    private val spanCount: Int
        get() {
            val width = window.decorView.width
            return if (width <= SCREEN_WIDTH_WITH_DEFAULT_SPAN) {
                DEFAULT_SPAN
            } else {
                val min = resources.getDimensionPixelSize(R.dimen.wallpaper_item_min_width)
                (width / min)
            }
        }
}