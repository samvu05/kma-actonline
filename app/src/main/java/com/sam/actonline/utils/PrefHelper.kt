package com.sam.actonline.utils

import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */

class PrefHelper(private val pref: SharedPreferences) {

    companion object {
        const val APP_PREFS = "COM_SAM_MYACT"
        const val ACCESS_TOKEN = "TOKEN"
        const val PRIVATE_TOKEN = "PRIVATE_TOKEN"
        const val USER_ID = "SITE_USER_ID"
    }

    fun logout() {
        token = ""
    }

    val checkToken: Boolean = !token.isBlank()

    var token: String
        get() = pref.getString(ACCESS_TOKEN, "")!!
        set(value) {
            pref.edit().putString(ACCESS_TOKEN, value).apply()
        }

    var privateToken: String
        get() = pref.getString(PRIVATE_TOKEN, "")!!
        set(value) {
            pref.edit().putString(PRIVATE_TOKEN, value).apply()
        }

    var userID: Int
        get() = pref.getInt(USER_ID, 0)
        set(value) {
            pref.edit().putInt(USER_ID, value).apply()
        }

    var isRememberPassword: Boolean
        get() = pref.getBoolean("isRememberPassword", false)
        set(value) = pref.edit().putBoolean("isRememberPassword", value).apply()

    var sortedFunctions: String
        get() {
            return pref.getString("sortedFunctions", "1234").toString()
        }
        set(value) {
            pref.edit().putString("sortedFunctions", value).apply()
        }

}