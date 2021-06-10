package com.sam.actonline.model.course

import com.google.gson.annotations.SerializedName

/**
 * Created by Dinh Sam Vu on 5/11/2021.
 */

data class ItemCourse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("fullname")
    val fullname: String,

    @SerializedName("enrolledusercount")
    val enrolledusercount: Int,

    @SerializedName("summary")
    val summary: String,

    @SerializedName("visible")
    val visible: Int,

    @SerializedName("format")
    val format: String,

    @SerializedName("showgrades")
    val showgrades: Boolean,

    @SerializedName("category")
    val category: Int,

    @SerializedName("progress")
    val progress: Double,

    @SerializedName("completed")
    val completed: Boolean,

    @SerializedName("startdate")
    val startdate: Int,

    @SerializedName("enddate")
    val enddate: Int,

    @SerializedName("isfavourite")
    val isfavourite: Boolean,

    @SerializedName("lastaccess")
    val lastaccess: Int,

    @SerializedName("errorcode")
    val errorcode: String?,

    @SerializedName("overviewfiles")
    val overviewfiles : List<CourseFile>

)