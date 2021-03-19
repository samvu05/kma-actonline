package com.example.actonline.base

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.actonline.database.MyDatabaseRepo
import com.example.actonline.utils.Constant
import com.example.actonline.utils.PreferenceHelper

/**
 * Created by Dinh Sam Vu on 3/3/2021.
 */
class BaseActivity : AppCompatActivity() {
    protected lateinit var preferenceHelper: PreferenceHelper
    protected lateinit var repoDatabase: MyDatabaseRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceHelper = PreferenceHelper(this, Constant.PREFERENCE_NAME_APP)
        repoDatabase = MyDatabaseRepo(this)
    }

    protected fun notifyUser(context: Context, mess: String) {
        Toast.makeText(context, mess, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}