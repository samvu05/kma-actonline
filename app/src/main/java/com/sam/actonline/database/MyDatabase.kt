@file:Suppress("DEPRECATION")

package com.sam.actonline.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sam.actonline.model.Site

/**
 * Created by Dinh Sam Vu on 3/18/2021.
 */

@Database(
    entities = [Site::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun siteDao(): SiteDao
}