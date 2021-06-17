package com.eup.jaemy.ui.setting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eup.jaemy.ui.setting.event.ItemTouchHelperAdapter
import com.sam.actonline.databinding.ItemFunctionHomeTitleBinding
import com.sam.actonline.model.Function
import java.util.*

/**
 * Created by Dinh Sam Vu on 4/8/2021.
 */
class FunctionAdapter(
    private val sortedCallback: (sortedID: String) -> Unit,
) :
    RecyclerView.Adapter<FunctionAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    private var listItem = mutableListOf<Function>()

    class ViewHolder(val binding: ItemFunctionHomeTitleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFunctionHomeTitleBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItem[position]
        holder.binding.apply {
            txtTitleFunction.text = item.title
        }
    }

    fun updateData(listSearch: MutableList<Function>) {
        this.listItem.clear()
        this.listItem.addAll(listSearch)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listItem.size

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(listItem, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)

        var sortedID = ""
        for (i in 0..3) {
            sortedID += listItem[i].id.toString()
        }
        sortedCallback(sortedID)
        return true
    }

    override fun onItemDismiss(position: Int) {
        listItem.removeAt(position)
        notifyItemRemoved(position)
    }
}