package com.example.actonline.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */

//@Database(
//    entities = [],
//    version = 1,
//    exportSchema = false
//)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: MyDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): MyDatabase {
            if (instance == null) {
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                )
                instance = builder
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!

        }
    }

}