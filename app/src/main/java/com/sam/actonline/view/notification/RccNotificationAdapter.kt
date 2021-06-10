package com.sam.actonline.view.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sam.actonline.databinding.ItemRccNotificationBinding
import com.sam.actonline.model.ItemNotification

/**
 * Created by Dinh Sam Vu on 3/3/2021.
 */

class RccNotificationAdapter(
    private var listData: List<ItemNotification>
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

    fun setData(data: List<ItemNotification>) {
        data.also { it.also { it.also { this.listData = it } } }
        notifyDataSetChanged()
    }
}