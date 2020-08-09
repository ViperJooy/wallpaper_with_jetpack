package com.viper.wallpaper.ui.wallpaper.adapter

import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.squareup.picasso.Picasso
import com.stfalcon.imageviewer.StfalconImageViewer
import com.viper.wallpaper.R
import com.viper.wallpaper.logic.model.Record
import com.viper.wallpaper.utils.showToast

/**
 * Created by viper.
 * Time: 20-8-4 15:30:11
 * Description:
 */
class WallPaperAdapter(
    private val wallPaperList: List<Record>
) : RecyclerView.Adapter<WallPaperAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivWallPaper: ImageView = view.findViewById(R.id.ivWallPaper)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.wallpaper_item, parent, false)
        val holder = ViewHolder(view)

        val imageList = mutableListOf<String>()

        for (wallpaper in wallPaperList) {
//            val img = "https://w.wallhaven.cc/full/${record.i.substring(0, 2)}/wallhaven-${record.i}.jpg"
            val img = "https://th.wallhaven.cc/small/${wallpaper.i.substring(0, 2)}/${wallpaper.i}.jpg"
            imageList.add(img)
        }

        holder.ivWallPaper.setOnClickListener {
            val position = holder.adapterPosition
            StfalconImageViewer.Builder<String>(
                holder.ivWallPaper.context,
                imageList
            ) { view, image ->
                Picasso.get().load(image).into(view)
            }.withStartPosition(position)
                .withTransitionFrom(holder.ivWallPaper)
                .allowSwipeToDismiss(true)
                .show()
        }
        return holder
    }

    override fun getItemCount() = wallPaperList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wallpaper = wallPaperList[position]
        val img = "https://th.wallhaven.cc/small/${wallpaper.i.substring(0, 2)}/${wallpaper.i}.jpg"
        holder.ivWallPaper.load(img)
    }
}