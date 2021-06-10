package com.sam.actonline.view.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sam.actonline.R
import com.sam.actonline.databinding.ItemRccEventBinding
import com.sam.actonline.extention.enableMarque
import com.sam.actonline.extention.setHtmlTextClean
import com.sam.actonline.extention.setTextMarque
import com.sam.actonline.extention.setTextNormal
import com.sam.actonline.model.event.ItemEvent

/**
 * Created by Dinh Sam Vu on 5/23/2021.
 */

class RccEventAdapter(
    private var listData: List<ItemEvent>,
    private val onClick: (itemEvent: ItemEvent) -> Unit
) : RecyclerView.Adapter<RccEventAdapter.ViewHoler>() {

    class ViewHoler(val binding: ItemRccEventBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val binding =
            ItemRccEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoler(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        val item = listData[position]
        holder.binding.apply {

            when (item.eventtype) {
                "site" -> {
                    tvType.text = "Sự kiện toàn trường"
                    imvType.setImageResource(R.drawable.ic_event_site)
                }
                "course" -> {
                    tvType.text = "Sự kiện khoá học"
                    imvType.setImageResource(R.drawable.ic_event_course)
                }
                else -> {
                    tvType.text = "Sự kiện riêng"
                    imvType.setImageResource(R.drawable.ic_event_site)
                }
            }

            tvName.setTextMarque(item.name)

            tvDate.apply {
                setHtmlTextClean(item.formattedtime)
                enableMarque()
            }

            tvCourse.setTextNormal(item.course?.fullname)
            layoutCourse.visibility =
                if (item.course?.fullname.isNullOrBlank()) View.GONE else View.VISIBLE

            tvLocation.setTextNormal(item.location)
            layoutPosition.visibility =
                if (item.location.isNullOrBlank()) View.GONE else View.VISIBLE

            layoutContainer.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int = listData.size

    fun setData(data: List<ItemEvent>) {
        data.also { it.also { it.also { this.listData = it } } }
        notifyDataSetChanged()
    }
}
