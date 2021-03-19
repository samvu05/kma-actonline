package com.example.actonline.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */
class PreferenceHelper(private val context: Context, name: String) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
}