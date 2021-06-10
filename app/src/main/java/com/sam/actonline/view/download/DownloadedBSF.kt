package com.sam.actonline.view.download

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sam.actonline.base.BaseBottomSheet
import com.sam.actonline.databinding.BsfDownloadedBinding
import com.sam.actonline.extention.setOnCustomClick
import com.sam.actonline.extention.showLog
import com.sam.actonline.utils.Constant
import com.sam.actonline.utils.FileHelper
import java.io.File

/**
 * Created by Dinh Sam Vu on 6/10/2021.
 */
class DownloadedBSF : BaseBottomSheet<BsfDownloadedBinding>() {
    private val baseDirectory: String by lazy {
        requireActivity().getExternalFilesDir(null)!!.path + File.separator + Constant.DOWNLOADED_FILE_FOLDER
    }
    private val downloadFolder by lazy { File(baseDirectory) }
    private val mAdapter by lazy { DownloadedAdapter(arrayOf(), onFileClick, onFileLongClick) }

    override fun initView(view: View) {
        setupRcc()
        loadFile()
        onClick()
    }

    private fun onClick() {
        binding.apply {
            btnClose.setOnCustomClick {
                dismiss()
            }
        }
    }

    private val onFileClick: (file: File) -> Unit = {
        FileHelper.openFile(it, requireActivity())
    }

    private val onFileLongClick: (file: File) -> Unit = {
        FileHelper.shareFile(it, requireActivity())
    }

    private fun setupRcc() {
        binding.rccDownloaded.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun loadFile() {
        if (downloadFolder.isDirectory && !downloadFolder.listFiles().isNullOrEmpty()) {
            downloadFolder.listFiles()!!.forEach {
                if (it.listFiles().isNullOrEmpty()){
                    it.delete()
                }
            }

            mAdapter.setData(downloadFolder.listFiles()!!)

            showLog("ListFile: ${downloadFolder.listFiles()!!.size}")
            showLog("List: ${downloadFolder.list()!!.size}")
        }
    }
}