@file:Suppress("DEPRECATION")

package com.sam.actonline.utils

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import com.sam.actonline.extention.checkInternet
import com.sam.actonline.extention.showToast
import java.io.File

/**
 * Created by Dinh Sam Vu on 6/9/2021.
 */
object FileHelper {

    var lastMsg = ""
    var msg: String? = ""
    const val ROOT_FOLDER = "ACT EDU"
    val BASE_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path +
            File.separator +
            ROOT_FOLDER

    fun startDownloadFile(activity: Activity, fileurl: String?, fileName: String?) {
        if (activity.isFinishing || activity.isDestroyed) return
        if (activity.checkInternet()) {
            if (fileurl.isNullOrEmpty() || fileName.isNullOrEmpty()) {
                AlertHelper.showTipAlert(
                    context = activity,
                    title = "Oops !",
                    desc = "File có vấn đề"
                )
                return
            }

            //Android > Data > APP ID > Files > target Folder
            val targetDirectory =
                File(activity.getExternalFilesDir(null)!!.path + File.separator + Constant.DOWNLOADED_FILE_FOLDER)
            if (!targetDirectory.exists()) {
                targetDirectory.mkdir()
            }

            val downloadManager =
                activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadUri = Uri.parse(fileurl)

            val request = DownloadManager.Request(downloadUri).apply {
                setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle("ACT EDUCATION")
                    .setDescription("Downloading File")
                    .setDestinationInExternalFilesDir(
                        activity,
                        "Downloaded File",
                        fileName
                    )
            }

            val downloadId = downloadManager.enqueue(request)
            val query = DownloadManager.Query().setFilterById(downloadId)
            Thread(kotlinx.coroutines.Runnable {
                var downloading = true
                while (downloading) {
                    val cursor: Cursor = downloadManager.query(query)
                    cursor.moveToFirst()
                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false
                    }
                    val status =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                    msg = statusMessage(status)
                    if (msg != lastMsg) {
                        activity.runOnUiThread {
                            activity.showToast(msg + "")
                        }
                        lastMsg = msg ?: ""
                    }
                    activity.runOnUiThread {
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
//                            bindingAV.downloadAudioBtn.setImageResource(R.drawable.ic_delete_file)
//                            model.taskAudioInsert(itemAudio)
                            activity.showToast("Done")
                        }
                    }
                    cursor.close()
                }

            }).start()
        } else {
            activity.showToast("Co loi xay ra")
        }
    }

    private fun statusMessage(status: Int): String {
        return when (status) {
            DownloadManager.STATUS_FAILED -> "Download has been failed, please try again"
            DownloadManager.STATUS_PAUSED -> "Paused"
            DownloadManager.STATUS_PENDING -> "Pending..."
            DownloadManager.STATUS_RUNNING -> "Downloading..."
            DownloadManager.STATUS_SUCCESSFUL -> "Downloaded successfully !"
            else -> "There's nothing to download"
        }
    }
}