package com.viper.wallpaper.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.viper.wallpaper.MainActivity
import com.viper.wallpaper.R
import com.viper.wallpaper.logic.model.Record
import com.viper.wallpaper.utils.showToast
import kotlin.math.log

/**
 * Created by viper.
 * Time: 20-8-4 15:30:11
 * Description:
 */
class WallPaperAdapter(
    private val activity: MainActivity,
    private val wallPaperList: List<Record>
) : RecyclerView.Adapter<WallPaperAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivWallPaper: ImageView = view.findViewById(R.id.ivWallPaper)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.wallpaper_item, parent, false)
        val holder = ViewHolder(view)
        holder.ivWallPaper.setOnClickListener {
            val position = holder.adapterPosition
            val wallpaper = wallPaperList[position]
            wallpaper.i.showToast(parent.context)
        }
        return holder
    }

    override fun getItemCount() = wallPaperList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wallpaper = wallPaperList[position]
        val img = "https://th.wallhaven.cc/small/${wallpaper.i.substring(0,2)}/${wallpaper.i}.jpg"
        holder.ivWallPaper.load(img)

    }
}