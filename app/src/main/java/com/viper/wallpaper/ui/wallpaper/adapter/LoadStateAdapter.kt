package com.viper.wallpaper.ui.wallpaper.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

/**
 * Created by viper.
 * Time: 20-8-18 23:19:03
 * Description:
 */
class LoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder = LoadStateViewHolder(parent, retry)


    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
        holder.bindTo(loadState)

}