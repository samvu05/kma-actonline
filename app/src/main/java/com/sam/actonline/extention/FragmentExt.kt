package com.sam.actonline.extention


import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */

fun Fragment.showToast(mes: String) {
    if (activity != null)
        Toast.makeText(activity, mes, Toast.LENGTH_SHORT).show()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}