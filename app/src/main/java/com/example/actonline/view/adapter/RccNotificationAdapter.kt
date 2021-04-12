package com.example.actonline.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actonline.databinding.ItemRccNotificationBinding
import com.example.actonline.model.ItemNotification

/**
 * Created by Dinh Sam Vu on 3/3/2021.
 */

class RccNotificationAdapter(
    private var listData: MutableList<ItemNotification>
) : RecyclerView.Adapter<RccNotificationAdapter.ViewHoler>() {

    class ViewHoler(val binding: ItemRccNotificationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val binding =
            ItemRccNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoler(binding)
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun setAdapterData(data: MutableList<ItemNotification>) {
        data.also { it.also { it.also { this.listData = it } } }
        notifyDataSetChanged()
    }
}
