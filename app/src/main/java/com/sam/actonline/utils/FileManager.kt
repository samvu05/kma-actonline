package com.sam.actonline.utils

import android.app.Activity
import android.app.DownloadManager
import android.content.*
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import com.sam.actonline.BuildConfig
import com.sam.actonline.extention.toDownloadLink2
import com.sam.actonline.model.coursedetail.Content
import com.sam.actonline.model.coursedetail.Module
import java.io.File

/**
 * Created by Dinh Sam Vu on 6/9/2021.
 */
@Suppress("DEPRECATION")
class FileManager(
    private val activity: Activity,
    private val course: String,
    private val onFinishDownload: (String) -> Unit,
) {

    companion object {
        //Folder inside public / Downloads
        private const val ROOT_FOLDER = "ACT Elearning"
    }

    private val courseName: String = course.replace("/".toRegex(), "_")

    private val baseDirectory: String
        get() = activity.getExternalFilesDir(null)!!.path + File.separator + ROOT_FOLDER

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
        downloadFile(content.fileurl.toDownloadLink2(), content.filename, module.description)
    }

    fun openModuleContent(content: Content) =
        openFile(content.filename)

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

    private fun openFile(fileName: String) {
        val file = File(baseDirectory, getRelativePath(fileName))
        val fileUri =
            FileProvider.getUriForFile(activity, "${BuildConfig.APPLICATION_ID}.provider", file)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(fileUri, FileUtils.getFileMimeType(fileName))
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        try {
            activity.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            intent.setDataAndType(fileUri, "application/*")
            activity.startActivity(
                Intent.createChooser(
                    intent,
                    "No Application found to open File - $fileName"
                )
            )
        }
    }

    fun shareModuleContent(content: Content) =
        shareFile(content.filename)

    private fun shareFile(fileName: String) {
        val file = File(baseDirectory, getRelativePath(fileName))
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
                "No app found to share the file - $fileName",
                Toast.LENGTH_SHORT
            ).show()
        }
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
}