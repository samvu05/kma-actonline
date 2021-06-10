package com.sam.actonline.utils

import android.net.Uri

/**
 * Created by Dinh Sam Vu on 5/15/2021.
 */
object UrlHelper {

    @JvmField
    val MOODLE_URL: Uri = with(Uri.Builder()) {
        scheme("https")
        authority("rims.fun")
        path("")
        build()
    }

    fun isMoodleUrl(url: Uri) = url.authority == MOODLE_URL.authority

    fun isCourseSectionUrl(url: Uri): Boolean {
        if (!isMoodleUrl(url)) return false;
        return url.path ?: "" == "/course/view.php";
    }


    fun isCourseModuleUrl(url: Uri): Boolean {
        if (!isMoodleUrl(url)) return false;
        return (url.path ?: "").matches(Regex("/mod/.*/view.php"))
    }

    fun isForumDiscussionUrl(url: Uri): Boolean {
        if (!isMoodleUrl(url)) return false;
        return url.path ?: "" == "/mod/forum/discuss.php";
    }

    fun getModIdFromUrl(url: Uri): Int {
        if (!isCourseModuleUrl(url)) return -1
        return url.getQueryParameter("id")?.toIntOrNull() ?: -1
    }

    fun getSectionNumFromUrl(url: Uri): Int {
        if (!isCourseSectionUrl(url)) return 0
        val fragment = url.fragment ?: ""
        var ret = 0
        if (fragment.startsWith("section-")) {
            ret = fragment.substringAfter("-").toIntOrNull() ?: 0
        }
        return ret
    }
}