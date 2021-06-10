package com.sam.actonline.view.download

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sam.actonline.databinding.ItemRccDownloadBinding
import com.sam.actonline.databinding.ItemRccDownloadFileBinding
import com.sam.actonline.extention.setOnCustomClick
import com.sam.actonline.extention.setTextMarque
import com.sam.actonline.extention.toDateTimeFromLong
import com.sam.actonline.utils.FileHelper
import java.io.File

/**
 * Created by Dinh Sam Vu on 6/10/2021.
 */
class DownloadedAdapter(
    private var listData: Array<File>,
    private val onClick: (file: File) -> Unit,
    private val onLongClick: (file: File) -> Unit
) : RecyclerView.Adapter<DownloadedAdapter.ViewHoler>() {
    class ViewHoler(val binding: ItemRccDownloadBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val binding =
            ItemRccDownloadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoler(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        val item = listData[position]
        holder.binding.apply {
            tvCourse.setTextMarque(item.name)

            val childAdapter = ChildFileAdapter(arrayOf(), onClick, onLongClick)
            rccChildFile.apply {
                adapter = childAdapter
            }
            if (!item.listFiles().isNullOrEmpty()) {
                childAdapter.setData(item.listFiles()!!)
            }
        }
    }

    override fun getItemCount(): Int = listData.size

    fun setData(data: Array<File>) {
        data.also { it.also { it.also { this.listData = it } } }
        notifyDataSetChanged()
    }

    fun reload() {
        notifyDataSetChanged()
    }


    //    Adapter for children File
    inner class ChildFileAdapter(
        private var listData: Array<File>,
        private val onClick: (file: File) -> Unit,
        private val onLongClick: (file: File) -> Unit
    ) : RecyclerView.Adapter<ChildFileAdapter.ViewHoler>() {

        inner class ViewHoler(val binding: ItemRccDownloadFileBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
            val binding =
                ItemRccDownloadFileBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return ViewHoler(binding)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHoler, position: Int) {
            val item = listData[position]
            holder.binding.apply {
                holder.binding.apply {
                    imvFile.setImageResource(FileHelper.getDrawableIconFromFileName(item.name))
                    tvName.setTextMarque(item.name)
                    tvFileSize.text = (item.length() / 1024).toString() + " KB"
                    tvDateDownloaded.text = item.lastModified().toDateTimeFromLong()

                    wraper.setOnCustomClick {
                        onClick(item)
                    }

                    wraper.setOnLongClickListener {
                        onLongClick(item)
                        true
                    }
                }
            }
        }

        override fun getItemCount(): Int = listData.size

        fun setData(data: Array<File>) {
            data.also { it.also { it.also { this.listData = it } } }
            notifyDataSetChanged()
        }
    }
}
