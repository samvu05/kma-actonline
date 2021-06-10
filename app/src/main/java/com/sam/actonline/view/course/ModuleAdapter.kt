package com.sam.actonline.view.course

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sam.actonline.R
import com.sam.actonline.databinding.ItemRccModuleBinding
import com.sam.actonline.extention.*
import com.sam.actonline.model.coursedetail.Module
import com.sam.actonline.utils.FileHelper

/**
 * Created by Dinh Sam Vu on 6/6/2021.
 */
class ModuleAdapter(
    private var data: List<Module>,
    private val onModuleClick: (item: Module) -> Unit,
    private val fileHelper: FileHelper
) : RecyclerView.Adapter<ModuleAdapter.ViewHoler>() {

    class ViewHoler(val binding: ItemRccModuleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val binding =
            ItemRccModuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoler(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        val item = data[position]
        holder.binding.apply {

            tvName.setHtmlTextClickable(item.name)
            tvDes.setHtmlTextClickable(item.description)
            if (item.description.isNullOrEmpty()) line1.toGone() else line1.toVisible()
            imvModule.setImageResource(item.moduleIcon)

            if (!item.canDownload) {
                imvDownload.setImageResource(R.drawable.ic_watch_more)
            } else {
                val downloaded = data[position].contents.stream().allMatch {
                    it.let { fileHelper.isModuleContentDownloaded(it) }
                }
                if (downloaded) {
                    imvDownload.setImageResource(R.drawable.ic_downloaded_from_clould)
                } else {
                    imvDownload.setImageResource(R.drawable.ic_download_from_clould)
                }
            }

            wraper.setOnCustomClickAnimPerson(container) {
                onModuleClick(item)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: MutableList<Module>) {
        data.also { it.also { it.also { this.data = it } } }
        notifyDataSetChanged()
    }
}