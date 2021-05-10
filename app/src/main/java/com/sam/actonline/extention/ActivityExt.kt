package com.sam.actonline.extention

import android.app.Activity
import android.view.View
import android.widget.Toast

/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */

fun Activity.showToast(mes: String) {
    Toast.makeText(this, mes, Toast.LENGTH_SHORT).show()
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}