package com.sam.actonline.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Dinh Sam Vu on 4/14/2021.
 */

@Entity(tableName = "table_site")
data class Site(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "sitename")
    @SerializedName("sitename")
    val siteName: String,

    @ColumnInfo(name = "username")
    @SerializedName("username")
    var userName: String,

    @ColumnInfo(name = "firstname")
    @SerializedName("firstname")
    val firsName: String,

    @ColumnInfo(name = "lastname")
    @SerializedName("lastname")
    val lastName: String,

    @ColumnInfo(name = "fullname")
    @SerializedName("fullname")
    val fullName: String,

    @ColumnInfo(name = "lang")
    @SerializedName("lang")
    val lang: String,

    @ColumnInfo(name = "userid")
    @SerializedName("userid")
    val userId: Int,

    @ColumnInfo(name = "siteurl")
    @SerializedName("siteurl")
    val siteUrl: String,

    @ColumnInfo(name = "userpictureurl")
    @SerializedName("userpictureurl")
    val userPicture: String,

    @ColumnInfo(name = "downloadfiles")
    @SerializedName("downloadfiles")
    val downloadFiles: Int,

    @ColumnInfo(name = "uploadfiles")
    @SerializedName("uploadfiles")
    val uploadFiles: Int,

//    @SerializedName("advancedfeatures")
//    val advancedFeatures: List<SiteAdvanceFeatures>,

    @SerializedName("errorcode")
    val errorcode: String?
)