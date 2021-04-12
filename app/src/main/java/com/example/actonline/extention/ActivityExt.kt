package com.example.actonline.extention

import android.app.Activity
import android.widget.Toast

/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */

fun Activity.toast(mes: String) {
    Toast.makeText(this, mes, Toast.LENGTH_LONG).show()
}