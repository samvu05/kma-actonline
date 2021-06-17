package com.sam.actonline.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sam.actonline.databinding.ItemFunctionHomeBinding
import com.sam.actonline.extention.setOnCustomClick
import com.sam.actonline.model.Function

/**
 * Created by Dinh Sam Vu on 4/14/2021.
 */
class HomeFuncAdapter(
    private val onClick: (item: Function) -> Unit
) :
    RecyclerView.Adapter<HomeFuncAdapter.ViewHolder>() {
    private val listItem = mutableListOf<Function>()

    class ViewHolder(val binding: ItemFunctionHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFunctionHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItem[position]
        holder.binding.apply {
            imgFunction.setImageResource(item.resImg)
            txtTitleFunction.text = item.title
            txtTitleFunction.isSelected = true

            btnLayout.setOnCustomClick() {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int = listItem.size

    fun updateData(newList: List<Function>) {
        this.listItem.clear()
        this.listItem.addAll(newList)
        notifyDataSetChanged()
    }
}