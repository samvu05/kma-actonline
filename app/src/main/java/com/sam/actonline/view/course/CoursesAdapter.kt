package com.sam.actonline.view.course

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.sam.actonline.databinding.ItemRccCourseBinding
import com.sam.actonline.extention.setImageFromUrl
import com.sam.actonline.extention.setTextMarque
import com.sam.actonline.extention.toDateTimeFromTimeStamp
import com.sam.actonline.model.course.ItemCourse
import com.sam.actonline.utils.Constant

/**
 * Created by Dinh Sam Vu on 5/12/2021.
 */

@Suppress("DEPRECATION")
class CoursesAdapter(
    private var data: MutableList<ItemCourse>,
    private val onClick: (item: ItemCourse) -> Unit
) : RecyclerView.Adapter<CoursesAdapter.ViewHoler>() {

    class ViewHoler(val binding: ItemRccCourseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val binding =
            ItemRccCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoler(binding)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        val item = data[position]
        holder.binding.apply {

            container.setOnClickListener {
                onClick(item)
            }

            tvTitle.setTextMarque(item.fullname)
            tvStartDate.setTextMarque(item.startdate.toDateTimeFromTimeStamp())
            tvEndDate.setTextMarque(item.enddate.toDateTimeFromTimeStamp())
            tvCountUser.setTextMarque(item.enrolledusercount.toString())

            var imvLink = ""
            if (item.overviewfiles.isNotEmpty()) {
                for (file in item.overviewfiles) {
                    if (file.fileurl.contains(".jpg") || file.fileurl.contains(".png")) {
                        imvLink = file.fileurl + Constant.ADD_LINK_1
                        break
                    }
                }
            }
            imvThumb.setImageFromUrl(imvLink)

            tvProgess.text = item.progress.toInt().toString() + "%"
            progress.progress = item.progress.toInt()
            val progessColor = when (item.progress.toInt()) {
                in 0..24 -> "#f44336"
                in 24..49 -> "#ff9800"
                in 50..74 -> "#ffeb3b"
                else -> "#8bc34a"
            }
            progress.progressTintList = ColorStateList.valueOf(progessColor.toColorInt())
        }
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: MutableList<ItemCourse>) {
        data.also { it.also { it.also { this.data = it } } }
        notifyDataSetChanged()
    }
}
