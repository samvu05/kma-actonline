package com.sam.actonline.view.course

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sam.actonline.databinding.ItemRccModuleBinding
import com.sam.actonline.extention.setHtmlTextClickable
import com.sam.actonline.model.coursedetail.Module

/**
 * Created by Dinh Sam Vu on 6/6/2021.
 */
class ModuleAdapter(
    private var data: List<Module>,
    private val onModuleClick: (item: Module) -> Unit
) : RecyclerView.Adapter<ModuleAdapter.ViewHoler>() {

    class ViewHoler(val binding: ItemRccModuleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val binding =
            ItemRccModuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoler(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        holder.binding.apply {
            tvName.setHtmlTextClickable(data[position].name)
            tvDes.setHtmlTextClickable(data[position].description)
            ivModule.setImageResource(data[position].moduleIcon)

            container.setOnClickListener{
                onModuleClick(data[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: MutableList<Module>) {
        data.also { it.also { it.also { this.data = it } } }
        notifyDataSetChanged()
    }
}