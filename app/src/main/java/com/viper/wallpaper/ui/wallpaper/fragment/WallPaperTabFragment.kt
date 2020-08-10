package com.viper.wallpaper.ui.wallpaper.fragment

import android.app.Activity
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.viper.wallpaper.R
import com.viper.wallpaper.logic.model.RequestData
import com.viper.wallpaper.ui.wallpaper.adapter.WallPaperAdapter
import com.viper.wallpaper.ui.wallpaper.viewmodel.WallPaperViewModel
import kotlinx.android.synthetic.main.fragment_wall_paper_tab.*

private const val ARG_TARGET = "arg_target"

class WallPaperTabFragment : Fragment() {
    private var param: String? = null

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(WallPaperViewModel::class.java)
    }
    private lateinit var wallPaperAdapter: WallPaperAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreateView")
        arguments?.let {
            param = it.getString(ARG_TARGET)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_wall_paper_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val requestData = RequestData(param, 1)
        viewModel.getJson(requestData)
        wallPaperAdapter =
            WallPaperAdapter(viewModel.wallPaperList)

        rvWallpaper.apply {
            layoutManager =
                StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
            adapter = wallPaperAdapter
        }

        viewModel.getJsonLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places = result.getOrNull()
            if (places != null) {
                viewModel.wallPaperList.clear()
                viewModel.wallPaperList.addAll(places)
                wallPaperAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "未获取到任何数据", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

    }


    private val spanCount: Int
        get() {
            val width = (context as Activity).window.decorView.width
            return if (width <= SCREEN_WIDTH_WITH_DEFAULT_SPAN) {
                DEFAULT_SPAN
            } else {
                val min = resources.getDimensionPixelSize(R.dimen.wallpaper_item_min_width)
                (width / min)
            }
        }


    companion object {
        private val TAG = WallPaperTabFragment::class.java.simpleName
        private const val DEFAULT_SPAN = 2
        private const val SCREEN_WIDTH_WITH_DEFAULT_SPAN = 1200

        @JvmStatic
        fun newInstance(target: String) =
            WallPaperTabFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TARGET, target)
                }
            }
    }

}