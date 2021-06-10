@file:Suppress("DEPRECATION")

package com.sam.actonline.utils

import android.os.Build
import android.text.Html
import com.sam.actonline.extention.setHtmlTextClean

/**
 * Created by Dinh Sam Vu on 5/25/2021.
 */
object Utils {

    var lastClickTime: Long = 0

    @JvmStatic
    fun isFastClick(): Boolean {
        val time = System.currentTimeMillis()
        if (time - lastClickTime < 1000) {
            return true
        }
        lastClickTime = time
        return false
    }

    fun getHtmlText(text: String?): String {
        if (text.isNullOrEmpty()) {
            return ""
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT).toString()
        } else {
            return Html.fromHtml(text).toString()
        }
    }
}