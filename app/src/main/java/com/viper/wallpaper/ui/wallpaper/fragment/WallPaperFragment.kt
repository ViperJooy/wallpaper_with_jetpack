package com.viper.wallpaper.ui.wallpaper.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.viper.wallpaper.R
import com.viper.wallpaper.logic.model.RequestData
import com.viper.wallpaper.ui.wallpaper.WallPaperItemDecoration
import com.viper.wallpaper.ui.wallpaper.adapter.WallPaperPagingAdapter
import com.viper.wallpaper.ui.wallpaper.viewmodel.WallPaperViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_wall_paper.*
import kotlinx.coroutines.flow.collectLatest

private const val ARG_TARGET = "arg_target"

@AndroidEntryPoint
class WallPaperFragment : Fragment() {
    private var param: String? = null

    //非依赖注入写法
//    private val viewModel by lazy {
//        ViewModelProviders.of(this).get(WallPaperViewModel::class.java)
//    }
    //使用hilt依赖注入写法
    private val viewModel: WallPaperViewModel by viewModels()

    private lateinit var wallPaperAdapter: WallPaperPagingAdapter
    lateinit var requestData: RequestData

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
        return inflater.inflate(R.layout.fragment_wall_paper, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        state.onRefresh {
            // 一般在这里进行网络请求
            requestData = RequestData(param, 1)

        }.showLoading()

        wallPaperAdapter = WallPaperPagingAdapter()

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
//            adapter = wallPaperAdapter.withLoadStateFooter(LoadStateAdapter(wallPaperAdapter::retry))
            adapter = wallPaperAdapter
        }

        viewModel.getWallPaperFlow(requestData)
        //获取数据并渲染UI
        viewModel.getWallPaperLiveData.observe(viewLifecycleOwner, Observer {
            state.showContent()
            lifecycleScope.launchWhenCreated {
                wallPaperAdapter.submitData(it)
            }
        })

        //监听刷新状态当刷新完成之后关闭刷新
        lifecycleScope.launchWhenCreated {
            wallPaperAdapter.loadStateFlow.collectLatest {
                if (it.refresh !is LoadState.Loading) {
                    refreshLayout.isRefreshing = false
                }
            }
        }
        refreshLayout.setOnRefreshListener {
            wallPaperAdapter.refresh()
        }
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
        private val TAG = WallPaperFragment::class.java.simpleName
        private const val DEFAULT_SPAN = 2
        private const val SCREEN_WIDTH_WITH_DEFAULT_SPAN = 1200

        @JvmStatic
        fun newInstance(target: String) =
            WallPaperFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TARGET, target)
                }
            }
    }

}