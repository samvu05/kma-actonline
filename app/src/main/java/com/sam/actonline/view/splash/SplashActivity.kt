package com.sam.actonline.view.splash

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.view.View
import androidx.core.animation.doOnEnd
import com.sam.actonline.base.BaseActivity
import com.sam.actonline.databinding.ActivitySplashBinding
import com.sam.actonline.utils.PrefHelper
import com.sam.actonline.view.login.LoginActivity
import com.sam.actonline.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Dinh Sam Vu on 4/18/2021.
 */

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    @Inject
    lateinit var pref: PrefHelper

    override fun initView() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)

        ObjectAnimator.ofPropertyValuesHolder(binding.imvLogo, scaleX, scaleY).run {
            duration = 1000
            start()
            doOnEnd {
                skipSplash()
            }
        }
    }

    private fun skipSplash() {
        if (pref.isRememberPassword && pref.checkToken) {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        } else {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
        finish()
    }

}