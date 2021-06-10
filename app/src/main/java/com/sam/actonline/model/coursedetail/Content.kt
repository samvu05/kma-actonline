package com.sam.actonline.model.coursedetail

import com.google.gson.annotations.SerializedName

/**t0
 * Created by Dinh Sam Vu on 5/15/2021.
 */
data class Content(
    @SerializedName("type")
    val type: String,

    @SerializedName("filename")
    val filename: String,

    @SerializedName("filepath")
    val filepath: String,

    @SerializedName("filesize")
    val filesize: Int,

    @SerializedName("fileurl")
    val fileurl: String,

    @SerializedName("timecreated")
    val timecreated: Int,

    @SerializedName("timemodified")
    val timemodified: Int,

    @SerializedName("sortorder")
    val sortorder: Int,

    @SerializedName("userid")
    val userid: Int,

    @SerializedName("author")
    val author: String,
    )
