package com.example.actonline.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */

//@Database(
//    entities = [ItemFeed::class, ItemWord::class, ItemWordHistory::class, ItemAudio::class],
//    version = 1,
//    exportSchema = false
//)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {
//    abstract fun itemFeedDao(): ItemFeedDao
//    abstract fun itemWordDao(): ItemWordDao
//    abstract fun itemWordHistoryDao(): ItemWordHistoryDao
//    abstract fun itemAudioDao(): ItemAudioDao

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