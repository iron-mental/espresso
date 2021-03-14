package com.wswon.picker

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class GridDividerDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        outRect.bottom = 9
        when (position % 3) {
            0 -> outRect.right = 6
            1 -> {
                outRect.left = 3
                outRect.right = 3
            }
            2 -> outRect.left = 6
        }
    }
}