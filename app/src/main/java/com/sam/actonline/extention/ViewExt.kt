package com.sam.actonline.extention

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View

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

fun View.setOnCustomClick(onFinishAnimation: () -> Unit) {
    this.setOnClickListener {
        it.isClickable = false
        val mScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.85f)
        val mScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.85f)
        val animator: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(this, mScaleX, mScaleY)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.duration = 100
        animator.start()

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                it.isClickable = true
                onFinishAnimation()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })
    }

}

fun View.setOnCustomClickAnimPerson(personView: View, onFinishAnimation: () -> Unit) {
    this.setOnClickListener {
        it.isClickable = false
        val mScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.85f)
        val mScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.85f)
        val animator: ObjectAnimator =
            ObjectAnimator.ofPropertyValuesHolder(personView, mScaleX, mScaleY)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.duration = 100
        animator.start()

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                it.isClickable = true
                onFinishAnimation()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })
    }

}