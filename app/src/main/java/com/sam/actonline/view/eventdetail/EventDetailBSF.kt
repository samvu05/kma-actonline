package com.sam.actonline.view.eventdetail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import androidx.fragment.app.viewModels
import com.sam.actonline.base.BaseBottomSheet
import com.sam.actonline.databinding.BsfEventDetailBinding
import com.sam.actonline.extention.*
import com.sam.actonline.model.event.ItemEventDetail
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


/**
 * Created by Dinh Sam Vu on 5/25/2021.
 */

@Suppress("DEPRECATION")
@AndroidEntryPoint
class EventDetailBSF : BaseBottomSheet<BsfEventDetailBinding>() {
    private var eventID = 0
    private var eventName = ""

    private var itemEvent: ItemEventDetail? = null
    private val model: EventDetailVM by viewModels()

    companion object {
        private const val EVENT_ID = "EVENT_ID"
        private const val EVENT_NAME = "EVENT_NAME"

        @JvmStatic
        fun newInstance(eventID: Int, eventName: String?) = EventDetailBSF().apply {
            arguments = Bundle().apply {
                putInt(EVENT_ID, eventID)
                putString(EVENT_NAME, eventName)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            eventID = it.getInt(EVENT_ID)
            eventName = it.getString(EVENT_NAME) ?: "Sự kiện"
        }
    }

    override fun initView(view: View) {
        obsever()
        onClick()

        if (eventID != 0) model.getEventDetail(eventID)
        binding.tvName.setTextMarque(eventName)
        showHidePlaceHolder(true)
    }

    private fun onClick() {
        binding.apply {
            btnClose.setOnClickListener {
                dismiss()
            }
            btnSaveEvent.setOnClickListener {
                insertEventToCalendar()
            }
            btnGoWeb.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(itemEvent?.viewurl)))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun obsever() {
        model.eventDetail.observe(this@EventDetailBSF) {
            setEventData(it)
        }
    }

    private fun insertEventToCalendar() {
        if (itemEvent == null || itemEvent?.name.isNullOrBlank() || itemEvent?.timestart.toString()
                .isBlank()
        ) {
            // TODO: Dialog báo lỗi cho người dùng
            return
        }

        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(
                CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                (itemEvent!!.timestart!!.toLong() * 1000)
            )
            putExtra(CalendarContract.Events.TITLE, itemEvent!!.name)
            putExtra(CalendarContract.Events.EVENT_LOCATION, itemEvent!!.location)
            putExtra(CalendarContract.Events.ALL_DAY, false)
        }

        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun setEventData(item: ItemEventDetail?) {
        if (item == null) {
            return
        }

        showHidePlaceHolder(false)
        itemEvent = item
        binding.apply {
            when (item.eventtype) {
                "site" -> {
                    tvType.text = "Sự kiện toàn trường"
//                    imvType.setImageResource(com.sam.actonline.R.drawable.ic_event_site)
                }
                "course" -> {
                    tvType.text = "Sự kiện khoá học"
//                    imvType.setImageResource(com.sam.actonline.R.drawable.ic_event_course)
                }
                else -> {
                    tvType.text = "Sự kiện chung"
//                    imvType.setImageResource(com.sam.actonline.R.drawable.ic_event_site)
                }
            }

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

            tvDescription.setHtmlTextClean(item.description)
            layoutDescription.visibility =
                if (item.description.isNullOrBlank()) View.GONE else View.VISIBLE
        }
    }

    private fun showHidePlaceHolder(isShow: Boolean) {
        if (isShow) {
            binding.apply {
                placeHolder.toVisible()
                layoutContent.toGone()
                btnGoWeb.toGone()
            }
        } else {
            binding.apply {
                placeHolder.toGone()
                layoutContent.toVisible()
                btnGoWeb.toVisible()
            }
        }
    }
}