package com.viper.wallpaper.ui.wallpaper.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.viper.wallpaper.ui.wallpaper.fragment.WallPaperTabFragment

/**
 * Created by viper.
 * Time: 20-8-9 11:13:57
 * Description:
 */
class WallPaperFragmentStateAdapter(
    private val tabs: Array<String>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        return WallPaperTabFragment.newInstance(tabs[position])
    }
}