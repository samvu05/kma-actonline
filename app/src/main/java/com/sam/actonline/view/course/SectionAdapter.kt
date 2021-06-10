package com.sam.actonline.view.course

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sam.actonline.databinding.ItemRccSectionBinding
import com.sam.actonline.extention.setHtmlTextClickable
import com.sam.actonline.model.coursedetail.ItemSection
import com.sam.actonline.model.coursedetail.Module

/**
 * Created by Dinh Sam Vu on 5/16/2021.
 */
@Suppress("DEPRECATION")
class SectionAdapter(
    private var data: MutableList<ItemSection>,
    private val onModuleClick: (item: Module) -> Unit
) : RecyclerView.Adapter<SectionAdapter.ViewHoler>() {

    class ViewHoler(val binding: ItemRccSectionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val binding =
            ItemRccSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoler(binding)
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        val item = data[position]
        holder.binding.apply {
            tvTitle.text = item.name
            tvSumary.setHtmlTextClickable(item.summary)
        }
        holder.binding.rccModule.apply {
            adapter = ModuleAdapter(item.modules, onModuleClick)
        }
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: MutableList<ItemSection>) {
        data.also { it.also { it.also { this.data = it } } }
        notifyDataSetChanged()
    }
}