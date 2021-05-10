package com.sam.actonline.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sam.actonline.databinding.ItemFunctionHomeBinding
import com.sam.actonline.model.ItemFunction

/**
 * Created by Dinh Sam Vu on 4/14/2021.
 */
class HomeFuncAdapter(
    private val onClick: (item: ItemFunction) -> Unit
) :
    RecyclerView.Adapter<HomeFuncAdapter.ViewHolder>() {
    private val listItem = mutableListOf<ItemFunction>()

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

            btnLayout.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int = listItem.size

    fun updateData(newList: List<ItemFunction>) {
        this.listItem.clear()
        this.listItem.addAll(newList)
        notifyDataSetChanged()
    }
}