package com.sam.actonline.extention

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sam.actonline.R
import com.google.android.material.snackbar.Snackbar

/**
 * Create by Dinh Sam Vu on 16/03/2021
 */

fun View.toVisible(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.toInVisible(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
    return this
}

fun View.toGone(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

/**
 * Extension method to get a view as bitmap.
 */
fun View.getBitmap(): Bitmap {
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    draw(canvas)
    canvas.save()
    return bmp
}

fun View.getString(stringResId: Int): String = resources.getString(stringResId)

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

fun View?.showSnackBar(
    @StringRes message: Int,
    @StringRes action: Int,
    listener: View.OnClickListener
) {
    if (null == this)
        return
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .setAction(action, listener)
        .setActionTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
        .show()
}

fun View.setOnCustomClickView(onViewClicked: () -> Unit) {
    val mScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.8f)
    val mScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.8f)

    val animator: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(this, mScaleX, mScaleY)
    animator.repeatCount = 1
    animator.repeatMode = ObjectAnimator.REVERSE

    animator.start()
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            onViewClicked()
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationRepeat(animation: Animator?) {
        }
    })
}
