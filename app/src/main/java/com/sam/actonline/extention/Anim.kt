package com.sam.actonline.extention

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.AccelerateInterpolator

/**
 * Created by Dinh Sam Vu on 3/29/2021.
 */

fun View.startRotateAnim(duration: Int) {
    val animator = ObjectAnimator.ofFloat(this, View.ROTATION, -360f, 0f)
    animator.duration = duration.toLong()
    animator.start()
}

fun View.startScaleAnim(scaleX: Float, scaleY: Float) {
    val mScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, scaleX)
    val mScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, scaleY)

    val animator: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(this, mScaleX, mScaleY)
    animator.repeatCount = 1
    animator.repeatMode = ObjectAnimator.REVERSE
    animator.start()
}

fun View.startFadeAnim() {
    val animator = ObjectAnimator.ofFloat(this, View.ALPHA, 0f)
    animator.repeatCount = 1
    animator.repeatMode = ObjectAnimator.REVERSE
    animator.start()
}

fun View.startScaleAndTranslateAnim(
    scaleX: Float,
    scaleY: Float,
    tranlateX: Float,
    tranlateY: Float,
    duration: Long,
    onAnimEnd: () -> Unit
) {
    val translater = ObjectAnimator.ofFloat(
        this, View.TRANSLATION_Y, tranlateY,
    )
    translater.interpolator = AccelerateInterpolator(1f)

    val mScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, scaleX)
    val mScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, scaleY)
    val scaler: ObjectAnimator =
        ObjectAnimator.ofPropertyValuesHolder(
            this,
            mScaleX,
            mScaleY
        )
    val set = AnimatorSet()
    set.playTogether(translater, scaler)
    set.duration = duration
    set.start()
    set.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            onAnimEnd()
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationRepeat(animation: Animator?) {
        }

    })
}


fun View.startTranslateAnimX(xPoint: Float) {
    val animator: ObjectAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, xPoint)
    animator.start()
}

fun View.startTranslateAnimX(xPoint: Float, onEndAnim: () -> Unit) {
    val animator: ObjectAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, xPoint)
    animator.start()
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {
        }

        override fun onAnimationEnd(animation: Animator?) {
            onEndAnim()
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationRepeat(animation: Animator?) {
        }

    })
}