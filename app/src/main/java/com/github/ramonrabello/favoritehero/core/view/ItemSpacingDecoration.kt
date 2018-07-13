package com.github.ramonrabello.favoritehero.core.view

import android.graphics.Rect
import android.support.annotation.IntRange
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * [android.support.v7.widget.RecyclerView.ItemDecoration] for placing
 * margins between items.
 *
 * @param leftOffset Left item offset
 * @param topOffset Top item offset
 * @param rightOffset Right item offset
 * @param bottomOffset Bottom item offset
 */
class ItemSpacingDecoration(@IntRange(from = 0) private var leftOffset: Int = 0,
                            @IntRange(from = 0) private var topOffset: Int = 0,
                            @IntRange(from = 0) private var rightOffset: Int = 0,
                            @IntRange(from = 0) private var bottomOffset: Int = 0) : RecyclerView.ItemDecoration() {

    /**
     * Set different margins for the items inside the recyclerView: no top margin for the first row
     * and no left margin for the first column.
     */
    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect?.set(leftOffset, topOffset, rightOffset, bottomOffset)
    }
}

