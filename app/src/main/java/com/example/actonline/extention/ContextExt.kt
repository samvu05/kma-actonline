package com.example.actonline.extention


import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */

fun Context.toast(mes: String) {
    Toast.makeText(this, mes, Toast.LENGTH_LONG).show()
}

// ẩn keyboars khi cham recyclview
@SuppressLint("ClickableViewAccessibility")
fun Context.hideKeyBoarsWhenScrollRcv(view: View) {
    view.setOnTouchListener { v, event ->
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
        false
    }
}

@SuppressLint("HardwareIds")
fun Context.getDeviceId(): String {
    return Settings.Secure.getString(
        this.contentResolver,
        Settings.Secure.ANDROID_ID
    )
}

