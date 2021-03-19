package com.example.actonline.database

import android.content.Context

/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */
class MyDatabaseRepo(context: Context) {
    private val mDatabase: MyDatabase = MyDatabase.getInstance(context)
}
