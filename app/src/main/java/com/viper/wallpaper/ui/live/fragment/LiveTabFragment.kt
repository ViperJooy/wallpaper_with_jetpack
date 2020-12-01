package com.viper.wallpaper.ui.live.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.viper.wallpaper.R
import com.viper.wallpaper.logic.model.RequestData
import com.viper.wallpaper.logic.model.live.douyu.Cate2Info
import com.viper.wallpaper.ui.live.adapter.LiveFragmentStateAdapter
import com.viper.wallpaper.ui.live.viewmodel.LiveViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_wall_paper_tab.*

@AndroidEntryPoint
class LiveTabFragment : Fragment() {

    //使用hilt依赖注入写法
    private val viewModel: LiveViewModel by viewModels()
    lateinit var requestData: RequestData

    companion object {
        private val TAG = LiveTabFragment::class.java.simpleName
        private val tabs = mutableListOf<String>()
        private val tabs_title = mutableListOf<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_live_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        requestData = RequestData("sa", 1)
        //获取数据并渲染UI
        viewModel.getLive(requestData)

        viewModel.getCateLiveFlowData.observe(viewLifecycleOwner, Observer { result ->
            var cate = result.getOrNull()
            if (cate != null) {
                showCateInfo(cate)
            }
        })


    }

    private fun showCateInfo(cate: List<Cate2Info>) {
        Log.d(TAG, "showCateInfo: ${cate.size}")

        for (i in cate) {
            tabs.add(i.shortName)
            tabs_title.add(i.cate2Name)
        }

        viewPager2.apply {
            offscreenPageLimit = 3
            adapter =
                LiveFragmentStateAdapter(tabs, childFragmentManager, lifecycle)
        }

        TabLayoutMediator(tab_layout, viewPager2) { tab, position ->
            tab.text = tabs_title[position]
            viewPager2.setCurrentItem(tab.position, true)
        }.attach()
    }

}