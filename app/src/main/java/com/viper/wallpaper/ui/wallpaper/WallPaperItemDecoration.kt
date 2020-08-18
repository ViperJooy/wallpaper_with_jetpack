package com.viper.wallpaper.ui.wallpaper

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by viper.
 * Time: 20-8-11 14:20:33
 * Description:
 */
class WallPaperItemDecoration(
    private val space: Int,
    private val spanSizeLookup: GridLayoutManager.SpanSizeLookup
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        if (spanSizeLookup.getSpanSize(position) == 1) {
            outRect.left = space
            if (position % 2 == 0) {
                outRect.right = space
            }
        }
    }
}