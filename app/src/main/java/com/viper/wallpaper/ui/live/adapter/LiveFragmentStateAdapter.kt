package com.viper.wallpaper.ui.live.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.viper.wallpaper.ui.live.fragment.LiveFragment
import com.viper.wallpaper.ui.wallpaper.fragment.WallPaperFragment

/**
 * Created by viper.
 * Time: 20-8-9 11:13:57
 * Description:
 */
class LiveFragmentStateAdapter(
    private val tabs: MutableList<String>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        return LiveFragment.newInstance(tabs[position])
    }
}