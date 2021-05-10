package com.sam.actonline.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sam.actonline.model.Site

/**
 * Created by Dinh Sam Vu on 5/10/2021.
 */

@Dao
interface SiteDao {
    @Insert
    suspend fun insert(site: Site)

    @Query("Select * from table_site where userId = :user")
    fun getSite(user: Int): LiveData<Site>

}