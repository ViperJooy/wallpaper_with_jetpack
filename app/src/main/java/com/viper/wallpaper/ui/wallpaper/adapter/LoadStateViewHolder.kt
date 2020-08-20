package com.viper.wallpaper.ui.wallpaper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.viper.wallpaper.R

/**
 * Created by viper.
 * Time: 20-8-18 23:20:09
 * Description:
 */
class LoadStateViewHolder(
    parent: ViewGroup,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.network_state_item, parent, false)
) {
    private val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)
    private val errorMsg = itemView.findViewById<TextView>(R.id.error_msg)
    private val retry = itemView.findViewById<Button>(R.id.retry_button)
        .also {
            it.setOnClickListener { retryCallback() }
        }

    fun bindTo(loadState: LoadState) {
        progressBar.isVisible = loadState is LoadState.Loading
        retry.isVisible = loadState is Error
        errorMsg.isVisible = !(loadState as? Error)?.message.isNullOrBlank()
        errorMsg.text = (loadState as? Error)?.localizedMessage
    }
}