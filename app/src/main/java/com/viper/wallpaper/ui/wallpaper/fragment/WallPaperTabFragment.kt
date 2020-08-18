package com.viper.wallpaper.ui.wallpaper.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.viper.wallpaper.R
import com.viper.wallpaper.logic.model.RequestData
import com.viper.wallpaper.ui.wallpaper.WallPaperItemDecoration
import com.viper.wallpaper.ui.wallpaper.adapter.WallPaperViewBinder
import com.viper.wallpaper.ui.wallpaper.viewmodel.WallPaperViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_wall_paper_tab.*
import javax.inject.Inject

private const val ARG_TARGET = "arg_target"

@AndroidEntryPoint
class WallPaperTabFragment : Fragment() {
    private var param: String? = null

    //非依赖注入写法
//    private val viewModel by lazy {
//        ViewModelProviders.of(this).get(WallPaperViewModel::class.java)
//    }
    //使用hilt依赖注入写法
    private val viewModel: WallPaperViewModel by viewModels()

    private lateinit var wallPaperAdapter: MultiTypeAdapter

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
        val requestData = RequestData(param, 3)
        viewModel.getJson(requestData)

        wallPaperAdapter = MultiTypeAdapter()
        wallPaperAdapter.register(
            WallPaperViewBinder(
                viewModel.wallPaperList
            )
        )
        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return spanCount
            }
        }

        rvWallpaper.apply {
            addItemDecoration(
                WallPaperItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.normal_space),
                    spanSizeLookup
                )
            )
            layoutManager = GridLayoutManager(context, spanCount)
            adapter = wallPaperAdapter
        }

        viewModel.getJsonLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places = result.getOrNull()
            if (places != null) {
                viewModel.wallPaperList.clear()
                viewModel.wallPaperList.addAll(places)
                wallPaperAdapter.items = viewModel.wallPaperList
                wallPaperAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "未获取到任何数据", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

    }


    private val spanCount: Int
        get() {
            val width = requireActivity().window.decorView.width
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