package com.sam.actonline.view.splash

import android.content.Intent
import com.sam.actonline.base.BaseActivity
import com.sam.actonline.databinding.FragmentMoreBinding
import com.sam.actonline.utils.PrefHelper
import com.sam.actonline.view.login.LoginActivity
import com.sam.actonline.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Dinh Sam Vu on 4/18/2021.
 */

@AndroidEntryPoint
class SplashActivity : BaseActivity<FragmentMoreBinding>() {

    @Inject
    lateinit var pref: PrefHelper

    override fun initView() {
        when {
            !pref.isRememberPassword -> {
                pref.logout()
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
            pref.isRememberPassword && pref.checkToken -> {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
            else -> startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
        finish()
    }
}