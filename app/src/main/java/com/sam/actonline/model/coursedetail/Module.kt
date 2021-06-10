package com.sam.actonline.model.coursedetail

import com.google.gson.annotations.SerializedName
import com.sam.actonline.R
import java.util.*

/**
 * Created by Dinh Sam Vu on 5/15/2021.
 */
data class Module(

    @SerializedName("id")
    val id: Int,

    @SerializedName("url")
    val url: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("contextid")
    val contextid: Int,

    @SerializedName("visible")
    val visible: Int,

    @SerializedName("uservisible")
    val uservisible: Boolean,

    @SerializedName("modicon")
    val modicon: String,

    @SerializedName("modname")
    val modname: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("contents")
    val contents: List<Content>
) {

    enum class Type {
        RESOURCE, FORUM, LABEL, ASSIGNMENT, FOLDER, QUIZ, URL, PAGE, DEFAULT, BOOK
    }

    val moduleIcon: Int
        get() {
            return when (getModuleType()) {
                Type.DEFAULT -> R.drawable.ic_sticky_note
                Type.RESOURCE -> R.drawable.ic_powerpoint
                Type.FOLDER -> R.drawable.ic_folder
                Type.BOOK -> R.drawable.ic_book
                Type.FORUM -> R.drawable.ic_forum
                Type.QUIZ -> R.drawable.ic_quiz
                Type.ASSIGNMENT -> R.drawable.ic_assignment
                Type.URL -> R.drawable.ic_link
                else -> R.drawable.ic_sticky_note
            }
        }

    val canDownload: Boolean
        get() = !contents.isNullOrEmpty() && getModuleType() in arrayOf(
            Type.RESOURCE
        )

    fun getModuleType(): Type {
        return when (modname.lowercase(Locale.ROOT)) {
            "resource" -> Type.RESOURCE
            "forum" -> Type.FORUM
            "label" -> Type.LABEL
            "assign" -> Type.ASSIGNMENT
            "folder" -> Type.FOLDER
            "quiz" -> Type.QUIZ
            "url" -> Type.URL
            "page" -> Type.PAGE
            else -> Type.DEFAULT
        }
    }
}