package com.sam.actonline.extention

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import java.util.regex.Pattern

/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */

fun String.isPhone(): Boolean {
    val p = "^1([34578])\\d{9}\$".toRegex()
    return matches(p)
}

/**
 * Extension method to check if String is Email.
 */
fun String.isEmail(): Boolean {
    val p = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)\$".toRegex()
    return matches(p)
}

/**
 * Extension method to check if String is Number.
 */
fun String.isNumeric(): Boolean {
    val p = "^[0-9]+$".toRegex()
    return matches(p)
}


fun String.replaceEscapeCharacter(): String {
    if (this.isEmpty()) return ""
    var countQuote = 0
    val checkRegex = Pattern.compile("\"").matcher(this)

    while (checkRegex.find()) {
        countQuote++
    }

    val regexPattern =
        if (countQuote % 2 == 0) {
            "[\\[\\]{}/*#$%&'^|_]"
        } else {
            "[\\[\\]\"{}/*#$%&'^|_]"
        }
    return this.replace(regexPattern.toRegex(), "").trim()
}


/**
 * Extension method to get the TAG name for all object
 */
fun <T : Any> T.TAG() = this::class.simpleName