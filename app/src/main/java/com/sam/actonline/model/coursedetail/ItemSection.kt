package com.sam.actonline.model.coursedetail

import com.google.gson.annotations.SerializedName

/**
 * Created by Dinh Sam Vu on 5/15/2021.
 */
data class ItemSection(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("visible")
    val visible: Int,

    @SerializedName("summary")
    val summary: String,

    @SerializedName("section")
    val section: Int,

    val uservisible: Boolean,

    @SerializedName("modules")
    val modules: List<Module>

)