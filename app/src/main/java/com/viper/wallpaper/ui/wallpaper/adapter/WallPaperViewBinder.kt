package com.viper.wallpaper.ui.wallpaper.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

class WallPaperViewBinder(private val wallPaperList: List<Record>) :
    ItemViewBinder<Record, WallPaperViewBinder.ViewHolder>(){
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(
            inflater.inflate(R.layout.wallpaper_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Record) {
        holder.setData(item)

        val imageList = mutableListOf<String>()

        for (wallpaper in wallPaperList) {
//            val img = "https://w.wallhaven.cc/full/${wallpaper.i.substring(0, 2)}/wallhaven-${wallpaper.i}.jpg"
            val img =
                "https://th.wallhaven.cc/small/${wallpaper.i.substring(0, 2)}/${wallpaper.i}.jpg"
            imageList.add(img)
        }

        holder.image.setOnClickListener {
            val position = holder.adapterPosition

            StfalconImageViewer.Builder<String>(
                holder.image.context,
                imageList
            ) { view, image ->
                view.load(image)
            }.withStartPosition(position)
//                .withTransitionFrom()
                .allowSwipeToDismiss(true)
                .show()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.ivWallPaper)
        fun setData(wallpaper: Record) {
            val img =
                "https://th.wallhaven.cc/small/${wallpaper.i.substring(0, 2)}/${wallpaper.i}.jpg"
            image.load(img)
        }
    }

}