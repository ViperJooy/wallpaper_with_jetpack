package com.viper.wallpaper.ui.wallpaper.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.drakeet.multitype.ItemViewBinder
import com.stfalcon.imageviewer.StfalconImageViewer
import com.viper.wallpaper.R
import com.viper.wallpaper.logic.model.Record

/**
 * Created by viper.
 * Time: 20-8-11 14:02:00
 * Description:
 */

class WallPaperPagingAdapter :
    PagingDataAdapter<Record, ViewHolder>(POST_COMPARATOR) {
    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Record>() {
            override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean =
                oldItem.i == newItem.i
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.i.let {
            val img = "https://th.wallhaven.cc/small/${it?.substring(0, 2)}/${it}.jpg"
            holder.image.load(img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.wallpaper_item, parent, false)
        )
    }

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.ivWallPaper)
}