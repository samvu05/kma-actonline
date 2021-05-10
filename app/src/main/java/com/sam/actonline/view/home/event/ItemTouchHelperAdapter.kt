package com.eup.jaemy.ui.setting.event

/**
 * Created by Dinh Sam Vu on 4/8/2021.
 */
interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    fun onItemDismiss(position: Int)
}