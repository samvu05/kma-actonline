package com.eup.jaemy.ui.setting.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eup.jaemy.ui.setting.event.ItemTouchHelperAdapter
import com.sam.actonline.R
import com.sam.actonline.databinding.ItemFunctionHomeTitleBinding
import com.sam.actonline.model.ItemFunction
import java.util.*

/**
 * Created by Dinh Sam Vu on 4/8/2021.
 */
class FunctionAdapter(
    private val sortedCallback: (sortedID: String) -> Unit, ) :
    RecyclerView.Adapter<FunctionAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    private var listItem = mutableListOf<ItemFunction>()
    private var isEditMode: Boolean = false

    class ViewHolder(val binding: ItemFunctionHomeTitleBinding) : RecyclerView.ViewHolder(binding.root)

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

            if (!isEditMode) {
                if (position in 0..3) {
                    txtTitleFunction.setTextColor(Color.parseColor("#FFFFFF"))
                    background.setBackgroundResource(R.color.text_color_primary_75)
                } else {
                    txtTitleFunction.setTextColor(Color.parseColor("#0783EF"))
                    background.setBackgroundResource(R.color.white)
                }
            } else {
                txtTitleFunction.setTextColor(Color.parseColor("#0783EF"))
                background.setBackgroundResource(R.color.white)
            }
        }
    }

    fun updateData(listSearch: MutableList<ItemFunction>) {
        this.listItem.clear()
        this.listItem.addAll(listSearch)
        notifyDataSetChanged()
    }

    fun switchEditMode(isEditMode: Boolean) {
        this.isEditMode = isEditMode
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