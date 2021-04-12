package com.example.actonline.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.actonline.model.AuthResponse
import io.reactivex.Completable

/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */

class PreferencesHelper(context: Context) {
    private val pref: SharedPreferences

    init {
        pref = context.getSharedPreferences(Constant.PREFERENCE_NAME_APP, Context.MODE_PRIVATE)
    }

    companion object {
        const val ACCESS_TOKEN = "MOODLE_TOKEN"
        const val PRIVATE_TOKEN = "MOODLE_PRIVATE_TOKEN"
        const val USER_ID = "MOODLE_SITE_USER_ID"
        const val PREFS = "MUST_MOODLE_SELF_ANDROID"
        const val DOWNLOAD_FOLDER = "MOODLE_DOWNLOAD_FOLDER"
    }

    fun logout() {
        pref.edit{
            ACCESS_TOKEN to null
            PRIVATE_TOKEN to null
        }
    }

    fun saveToken(authResponse: AuthResponse){
        pref.edit().putString(ACCESS_TOKEN,authResponse.token).apply()
        pref.edit().putString(PRIVATE_TOKEN,authResponse.token).apply()
    }

    var isShowIntro: Boolean
        get() {
            return pref.getBoolean("isShowIntro", true)
        }
        set(value) {
            pref.edit().putBoolean("isShowIntro", value).apply()
        }

    var isRememberPassword: Boolean
        get() = pref.getBoolean("isRememberPassword", false)
        set(value) = pref.edit().putBoolean("isRememberPassword", value).apply()

}