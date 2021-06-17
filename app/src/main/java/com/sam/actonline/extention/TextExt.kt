@file:Suppress("DEPRECATION")

package com.sam.actonline.extention


import android.os.Build
import android.text.Html
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.core.graphics.toColorInt
import com.sam.actonline.R
import com.sam.actonline.utils.Constant
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */

@Suppress("DEPRECATION")
fun TextView.setHtmlTextClean(html: String?) {
    if (html.isNullOrBlank()) {
        this.toGone()
        return
    }
    this.toVisible()
    html.replace("href", "")
        .replace("&raquo;", " ⇾ ")
        .run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                this@setHtmlTextClean.text = Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
            } else {
                this@setHtmlTextClean.text = Html.fromHtml(this)
            }
        }
}

fun TextView.setHtmlTextClickable(html: String?) {
    if (html.isNullOrBlank()) {
        this.toGone()
        return
    }
    this.toVisible()
    this.apply {
        text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(html)
        }
        setLinkTextColor("#1E88E5".toColorInt())
        movementMethod = LinkMovementMethod.getInstance()
    }
}


fun TextView.setTextNormal(_text: String?) {
    if (_text.isNullOrBlank()) {
        this.text = resources.getString(R.string.default_empty_text)
        return
    }
    this.text = _text
}

fun TextView.setTextMarque(_text: String?) {
    if (_text.isNullOrBlank()) {
        this.apply {
            text = resources.getString(R.string.default_empty_text)
        }
        return
    }
    this.apply {
        text = _text
        enableMarque()
    }
}

fun TextView.enableMarque() {
    this.apply {
        isSelected = true
        isSingleLine = true
        marqueeRepeatLimit = -1
        ellipsize = TextUtils.TruncateAt.MARQUEE
    }
}

fun String.toDownloadLink(): String =
    if (this.contains("?")) {
        this + Constant.ADD_LINK_2
    } else {
        this + Constant.ADD_LINK_1
    }

fun Int.toDateTimeFromTimeStamp(): String {
    return try {
        val template = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val netDate = Date(this.toLong() * 1000)
        template.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}

fun Long.toDateTimeFromLong(): String{
    return try {
        val template = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        template.format(this)
    } catch (e: Exception) {
        e.toString()
    }
}

fun String.isPhone(): Boolean {
    val p = "^1([34578])\\d{9}\$".toRegex()
    return matches(p)
}

fun String.isEmail(): Boolean {
    val p = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)\$".toRegex()
    return matches(p)
}

fun String.isNumber(): Boolean {
    val p = "^[0-9]+$".toRegex()
    return matches(p)
}




