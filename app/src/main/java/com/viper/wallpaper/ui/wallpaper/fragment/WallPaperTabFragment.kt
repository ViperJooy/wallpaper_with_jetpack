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
import com.viper.wallpaper.ui.wallpaper.adapter.LoadStateAdapter
import com.viper.wallpaper.ui.wallpaper.adapter.WallPaperPagingAdapter
import com.viper.wallpaper.ui.wallpaper.viewmodel.WallPaperViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_wall_paper_tab.*
import kotlinx.coroutines.flow.collectLatest

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

    //    private lateinit var wallPaperAdapter: MultiTypeAdapter
    private lateinit var wallPaperAdapter: WallPaperPagingAdapter

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
//        viewModel.getJson(requestData)

//        wallPaperAdapter = MultiTypeAdapter()
//        wallPaperAdapter.register(
//            WallPaperViewBinder(
//                viewModel.wallPaperList
//            )
//        )
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
//            adapter = wallPaperAdapter
            adapter = wallPaperAdapter.withLoadStateFooter(LoadStateAdapter(wallPaperAdapter::retry))
        }
//        rvWallpaper.adapter = wallPaperAdapter.withLoadStateFooter(PostsLoadStateAdapter(wallPaperAdapter))

//        viewModel.getJsonLiveData.observe(viewLifecycleOwner, Observer { result ->
//            val places = result.getOrNull()
//            if (places != null) {
//                viewModel.wallPaperList.clear()
//                viewModel.wallPaperList.addAll(places)
//                wallPaperAdapter.items = viewModel.wallPaperList
//                wallPaperAdapter.notifyDataSetChanged()
//            } else {
//                Toast.makeText(context, "未获取到任何数据", Toast.LENGTH_SHORT).show()
//                result.exceptionOrNull()?.printStackTrace()
//            }
//        })


        viewModel.getFlow(requestData)
        //获取数据并渲染UI
        viewModel.getFlowLiveData.observe(viewLifecycleOwner, Observer {
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

    //添加请求头验证
    private val map = mutableMapOf(
        "access" to "36a1b1711b3978f92ad4bf5f405f43d26cdd0a037eddfdc233d7674b9462e802",
        "location" to "bz.zzzmh.cn",
        "sign" to "50f9a39a0de9dc692baddf4d9ece3582",
        "timestamp" to "1596463830570",
        "Content-Type" to "application/json"
    )
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