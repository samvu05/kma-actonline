package com.sam.actonline.utils

import android.webkit.MimeTypeMap
import com.sam.actonline.R

/**
 * Created by Dinh Sam Vu on 6/9/2021.
 */
object FileUtils {

    fun getDrawableIconFromFileName(filename: String): Int {
        val mimeType = getFileMimeType(filename) ?: return -1
        return when (mimeType) {
            "application/pdf" -> R.drawable.file_pdf
            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" -> R.drawable.file_excel
            "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> R.drawable.file_word
            "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation" -> R.drawable.file_powerpoint
            else -> -1
        }
    }

    fun getFileMimeType(filename: String): String? {
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(getExtension(filename))
    }

    fun getExtension(filename: String): String {
        return filename.substring(filename.lastIndexOf('.') + 1)
    }
}