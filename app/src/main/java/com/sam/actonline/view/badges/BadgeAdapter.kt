package com.sam.actonline.view.badges

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.sam.actonline.databinding.ItemRccBadgeBinding
import com.sam.actonline.extention.*
import com.sam.actonline.model.ItemBadge

/**
 * Created by Dinh Sam Vu on 6/13/2021.
 */
class BadgeAdapter(
    private var data: MutableList<ItemBadge>,
    private val onClick: (item: ItemBadge) -> Unit
) : RecyclerView.Adapter<BadgeAdapter.ViewHoler>() {

    class ViewHoler(val binding: ItemRccBadgeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val binding =
            ItemRccBadgeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoler(binding)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        val item = data[position]
        holder.binding.apply {

            wrapper.setOnCustomClickAnimPerson(container) {
                onClick(item)
            }


            tvName.setTextNormal(item.name)
            tvAuthor.setTextMarque(item.issuername)
            ivMedal.setImageFromUrl(item.badgeurl?.toDownloadLink())
            tvDate.setTextMarque(item.timecreated?.toDateTimeFromTimeStamp())
            tvDes.setTextNormal(item.description)
        }
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: MutableList<ItemBadge>) {
        data.also { it.also { it.also { this.data = it } } }
        notifyDataSetChanged()
    }
}

