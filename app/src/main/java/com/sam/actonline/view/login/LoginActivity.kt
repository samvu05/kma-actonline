package com.sam.actonline.view.login

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.activity.viewModels
import androidx.core.graphics.toColorInt
import com.sam.actonline.base.BaseActivity
import com.sam.actonline.databinding.ActivityLoginBinding
import com.sam.actonline.extention.*
import com.sam.actonline.utils.AlertHelper
import com.sam.actonline.utils.PrefHelper
import com.sam.actonline.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val model: LoginVM by viewModels()

    @Inject
    lateinit var preferences: PrefHelper

    override fun initView() {
        setupView()
        onClick()
        observe()
    }

    private fun observe() {
        model.logged.observe(this@LoginActivity, {
            if (it) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } else {
                showToast("Đăng nhập thất bại, kiểm tra lại tài khoản, mật khẩu !")
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {
        binding.apply {
            boxSavePassword.isChecked = preferences.isRememberPassword
            edtPassword.transformationMethod = PasswordTransformationMethod()

            edtUsername.setText("ct010337")
            edtPassword.setText("Actvn@12345")
        }

    }

    private fun onClick() {
        binding.apply {
            btnLogin.setOnCustomClick() {
                hideKeyboard()
                login()
            }

            boxSavePassword.setOnCheckedChangeListener { _, isChecked ->
                preferences.isRememberPassword = isChecked
            }
        }
    }

    private fun login() {
        binding.apply {
            when {
                edtUsername.text.isNullOrBlank() -> {
                    showToast("Tên đăng nhập không được để trống !")
                }
                edtPassword.text.isNullOrBlank() -> {
                    showToast("Mật khẩu không được để trống !")
                }
                else -> model.login(edtUsername.text.toString(), edtPassword.text.toString())
            }
        }
    }
}