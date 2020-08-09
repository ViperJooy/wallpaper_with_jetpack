package com.viper.wallpaper.ui.wallpaper.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.viper.wallpaper.R
import com.viper.wallpaper.ui.wallpaper.adapter.WallPaperFragmentStateAdapter
import kotlinx.android.synthetic.main.fragment_wall_paper.*


class WallPaperFragment : Fragment() {

    companion object {
        private val TAG = WallPaperFragment::class.java.simpleName
        private val tabs = arrayOf("index", "people", "anime")
        private val tabs_title = arrayOf("精选", "人物", "二次元")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreateView")
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

        viewPager2.apply {
            offscreenPageLimit = 3
            adapter = WallPaperFragmentStateAdapter(tabs, childFragmentManager, lifecycle)
        }

        TabLayoutMediator(tab_layout, viewPager2) { tab, position ->
            tab.text = tabs_title[position]
        }.attach()
    }
}