@file:Suppress("DEPRECATION")

package com.sam.actonline.extention


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sam.actonline.utils.Constant

/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */

fun Activity.showToast(mes: String) {
    Toast.makeText(this, mes, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(mes: String) {
    if (activity != null)
        Toast.makeText(activity, mes, Toast.LENGTH_SHORT).show()
}

fun Context.hideKeyboard(view: View) {
    try {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    } catch (e: Exception) {
        Log.d("logDB", e.printStackTrace().toString())
    }

}

fun showLog(mes: String) {
    Log.d(Constant.DEAULT_LOGCAT, mes)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.startBrowser(url: String?) {
    if (url.isNullOrEmpty()) {
        return
    }
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}

fun Fragment.startBrowser(url: String?) {
    requireActivity().startBrowser(url)
}

fun Context?.checkInternet(): Boolean {
    if (this == null) return false
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}

inline fun <reified T : View> View.find(id: Int): T = findViewById(id) as T
inline fun <reified T : View> Activity.find(id: Int): T = findViewById(id) as T
inline fun <reified T : View> Fragment.find(id: Int): T = view?.findViewById(id) as T

@SuppressLint("HardwareIds")
fun Context.getDeviceId(): String {
    return Settings.Secure.getString(
        this.contentResolver,
        Settings.Secure.ANDROID_ID
    )
}





