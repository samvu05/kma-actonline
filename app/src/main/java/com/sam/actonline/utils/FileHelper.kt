package com.sam.actonline.utils

import android.app.Activity
import android.app.DownloadManager
import android.content.*
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.FileProvider
import com.sam.actonline.BuildConfig
import com.sam.actonline.R
import com.sam.actonline.extention.toDownloadLink
import com.sam.actonline.model.coursedetail.Content
import com.sam.actonline.model.coursedetail.Module
import java.io.File

/**
 * Created by Dinh Sam Vu on 6/9/2021.
 */
@Suppress("DEPRECATION")
class FileHelper(
    private val activity: Activity,
    course: String,
    private val onFinishDownload: (filename: String) -> Unit,
) {
    private val courseName: String = course.replace("/".toRegex(), "_")

    private val baseDirectory: String
        get() = activity.getExternalFilesDir(null)!!.path + File.separator + Constant.DOWNLOADED_FILE_FOLDER

    private val fileList: MutableList<String> = emptyList<String>().toMutableList()
    private val requestedDownloads: MutableList<String> = emptyList<String>().toMutableList()

    private val onComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            reloadFileList()
            for (filename in requestedDownloads) {
                if (isFileDownloaded(filename)) {
                    requestedDownloads.remove(filename)
                    onFinishDownload.invoke(filename)
                    return
                }
            }
        }
    }

    fun downloadModuleContent(content: Content, module: Module) {
        deleteExistingModuleContent(content)
        downloadFile(
            content.fileurl.toDownloadLink(),
            content.filename,
            module.description ?: ". . ."
        )
    }

    fun openModuleContent(content: Content) {
        val file = File(baseDirectory, getRelativePath(content.filename))
        openFile(file, activity)
    }

    private fun downloadFile(fileUrl: String, fileName: String, description: String) {
        val downloadUri = Uri.parse(fileUrl)
        val targetDirectory = Uri.fromFile(File(baseDirectory, getRelativePath(fileName)))

        val request = DownloadManager.Request(downloadUri).apply {
            setDescription(description)
                .setTitle(fileName)
                .setDestinationUri(targetDirectory)
        }

        val downloadManager = activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request) //download ID
        requestedDownloads.add(fileName)
    }


    fun shareModuleContent(content: Content) {
        val file = File(baseDirectory, getRelativePath(content.filename))
        shareFile(file,activity)
    }


    private fun deleteExistingModuleContent(content: Content) = deleteExistingFile(content.filename)

    private fun deleteExistingFile(fileName: String) {
        val file = File(baseDirectory, getRelativePath(fileName))
        if (file.exists()) {
            file.delete()
        }
    }

    fun reloadFileList() {
        fileList.clear()
        val courseDir = File(baseDirectory, getRelativePath(""))
        if (courseDir.isDirectory) {
            val files = courseDir.list()
            if (files != null) {
                fileList.addAll(listOf(*files))
            }
        }
    }

    private fun getRelativePath(filename: String) =
        File.separator + courseName + File.separator + filename

    fun isModuleContentDownloaded(content: Content) = isFileDownloaded(content.filename)

    fun isFileDownloaded(fileName: String): Boolean {
        if (fileList.isEmpty()) {
            reloadFileList()
        }
        return fileList.contains(fileName)
    }

    fun registerDownloadReceiver() =
        activity.registerReceiver(
            onComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )

    fun unregisterDownloadReceiver() =
        activity.unregisterReceiver(onComplete)

    companion object {

        @JvmStatic
        fun getDrawableIconFromFileName(filename: String): Int {
            val mimeType = getFileMimeType(filename) ?: return -1
            return when (mimeType) {
                "application/pdf" -> R.drawable.ic_powerpoint
                "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" -> R.drawable.ic_exel
                "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> R.drawable.ic_word
                "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation" -> R.drawable.ic_powerpoint
                "application/octet-stream",  -> R.drawable.ic_coding
                "image/jpeg" -> R.drawable.ic_image
                else -> R.drawable.ic_folder
            }
        }

        @JvmStatic
        fun getFileMimeType(filename: String): String? {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(getExtension(filename))
        }

        @JvmStatic
        fun getExtension(filename: String): String {
            return filename.substring(filename.lastIndexOf('.') + 1)
        }

        @JvmStatic
        fun openFile(file: File, activity: Activity) {
            val fileUri =
                FileProvider.getUriForFile(activity, "${BuildConfig.APPLICATION_ID}.provider", file)

            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(fileUri, getFileMimeType(file.name))
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            try {
                activity.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                intent.setDataAndType(fileUri, "application/*")
                activity.startActivity(
                    Intent.createChooser(
                        intent,
                        "No Application found to open File - ${file.name}"
                    )
                )
            }
        }


        @JvmStatic
        fun shareFile(file: File, activity: Activity) {
            val fileUri = FileProvider.getUriForFile(
                activity,
                "${BuildConfig.APPLICATION_ID}.provider",
                file
            )

            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            sendIntent.type = "application/*"
            try {
                activity.startActivity(Intent.createChooser(sendIntent, "Share File"))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    activity,
                    "No app found to share the file - ${file.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}