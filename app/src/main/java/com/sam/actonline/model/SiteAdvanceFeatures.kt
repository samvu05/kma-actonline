package com.sam.actonline.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

/**
 * Created by Dinh Sam Vu on 4/14/2021.
 */
data class SiteAdvanceFeatures(
    @SerializedName("name")
    val name: String,

    @SerializedName("value")
    val value: Int
)