package com.sam.actonline.view.calendar

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.google.android.material.snackbar.Snackbar
import com.sam.actonline.R
import com.sam.actonline.base.BaseFragment
import com.sam.actonline.databinding.FragmentCalendarBinding
import com.sam.actonline.extention.toGone
import com.sam.actonline.extention.toVisible
import com.sam.actonline.model.event.ItemEvent
import com.sam.actonline.utils.Utils
import com.sam.actonline.view.eventdetail.EventDetailBSF
import com.sam.actonline.view.home.adapter.RccEventAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Dinh Sam Vu on 3/19/2021.
 */
@Suppress("DEPRECATION")
@AndroidEntryPoint
class CalendarFragment : BaseFragment<FragmentCalendarBinding>() {
    private val model: CalendarVM by viewModels()
    private val calendarView by lazy { binding.calendarView }
    private val currentPage by lazy { calendarView.currentPageDate.time }

    private val eventAdapter: RccEventAdapter by lazy {
        RccEventAdapter(
            mutableListOf(),
            rccEventClick
        )
    }

    override fun initView(view: View) {
        initRccEvent()
        onClick()
        obsever()
    }

    private fun obsever() {
        Calendar.getInstance().run {
            setSelectedDateText(this)
            model.getEventByMonth(this.get(Calendar.YEAR), this.get(Calendar.MONTH) + 1)
            model.getEventByDate(
                year = this.get(Calendar.YEAR),
                month = this.get(Calendar.MONTH) + 1,
                date = this.get(Calendar.DATE)
            )
        }
        showHidePlaceHolder(true)

        model.dateEvents.observe(this@CalendarFragment) {
            if (it.isNotEmpty()) {
                eventAdapter.setData(it)
                showHidePlaceHolder(false)
            } else {
                showEmptyHolder()
            }
        }

        model.monthEvents.observe(this@CalendarFragment) {
            if (it.isNotEmpty()) {
                binding.calendarView.setEvents(it)
            }
        }
    }

    private fun initRccEvent() {
        binding.rccEvent.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onClick() {
        calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                if (Utils.isFastClick()) {
                    Snackbar.make(binding.root, "Đợi một chút !", Snackbar.LENGTH_LONG)
                        .setAction("OK") {}
                        .show()
                    return
                }
                getDateEvent(eventDay)
            }
        })

        calendarView.setOnForwardPageChangeListener {
            model.getEventByMonth(currentPage.year + 1900, currentPage.month + 1)
        }

        calendarView.setOnPreviousPageChangeListener {
            model.getEventByMonth(currentPage.year + 1900, currentPage.month + 1)
        }
    }

    private fun getDateEvent(eventDay: EventDay) {
        val calendar: Calendar = eventDay.calendar
        setSelectedDateText(calendar)
        model.getEventByDate(
            year = calendar.get(Calendar.YEAR),
            month = calendar.get(Calendar.MONTH) + 1,
            date = calendar.get(Calendar.DATE)
        )

        showHidePlaceHolder(true)
    }

    private fun setSelectedDateText(calendar: Calendar) {
        val template = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        binding.tvToday.text = template.format(calendar.time)
    }

    private val rccEventClick: (item: ItemEvent) -> Unit = {
        val eventBSF = EventDetailBSF.newInstance(it.id, it.name)
        eventBSF.show(childFragmentManager, eventBSF.tag)
    }

    private fun showHidePlaceHolder(isShow: Boolean) {
        if (isShow) {
            binding.apply {
                binding.placeHolder.placeHolderIv.toGone()
                rccEvent.toGone()
                placeHolder.placeHolder.toVisible()
                placeHolder.placeHolderProgess.toVisible()
            }
        } else {
            binding.apply {
                placeHolder.placeHolder.toGone()
                rccEvent.toVisible()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showEmptyHolder() {
        binding.apply {
            placeHolder.placeHolderIv.setImageResource(R.drawable.img_maps_direction)
            placeHolder.placeHolderIv.toVisible()
            placeHolder.placeHolderTv.text = "Không có sự kiện nào !"
            placeHolder.placeHolderProgess.toGone()
            rccEvent.toGone()
            placeHolder.placeHolder.toVisible()
        }
    }

}