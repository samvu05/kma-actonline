package com.eup.jaemy.ui.setting.event

import android.graphics.Canvas
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * Created by Dinh Sam Vu on 4/8/2021.
 */
class MyItemTouchHelperCallback(private val adapter: ItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {
    companion object {
        const val ALPHA_FULL = 1f
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return if (recyclerView.layoutManager is GridLayoutManager) {
            val dragFlags =
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            val swipeFlags = 0
            makeMovementFlags(dragFlags, swipeFlags)
        } else {
            val dragFlags =
                ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags: Int = ItemTouchHelper.START or ItemTouchHelper.END

            makeMovementFlags(dragFlags, swipeFlags)
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        if (viewHolder.itemViewType != target.itemViewType) {
            return false
        }
        adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        viewHolder.itemView.alpha = ALPHA_FULL
        if (viewHolder is ItemTouchHelperViewHolder) {
            val itemTouchHelperViewHolder: ItemTouchHelperViewHolder = viewHolder
            itemTouchHelperViewHolder.onItemClear()
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder is ItemTouchHelperViewHolder) {
                val itemTouchHelperViewHolder: ItemTouchHelperViewHolder = viewHolder
                itemTouchHelperViewHolder.onItemSelected()
            }
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val alpha = ALPHA_FULL - abs(dX) / viewHolder.itemView.width
            viewHolder.itemView.alpha = alpha
            viewHolder.itemView.translationX = dX
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
}