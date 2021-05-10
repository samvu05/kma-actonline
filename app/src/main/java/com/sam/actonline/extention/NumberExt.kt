package com.sam.actonline.extention


import android.content.Context
import android.util.TypedValue
import kotlin.math.roundToInt


/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */

fun Float.dp2Px(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        context.resources.displayMetrics
    ).roundToInt()
}


fun Float.px2Dp(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        this, metrics
    ).toInt() /*from ww  w .  ja v a  2  s  .  c  o  m*/
}

fun Float.spToPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        context.resources.displayMetrics
    )
        .toInt()
}

/**
 * ========== INT ==========
 */

fun Long.toDuration(): String {
    val s: Long = this % 60
    val m: Long = this / 60 % 60
    val h: Long = this / (60 * 60) % 24

    return when {
        h > 0 -> String.format("%d:%02d:%02d", h, m, s)
        m > 0 -> String.format("%02d:%02d", m, s)
        else -> String.format("%02d", s)
    }
}