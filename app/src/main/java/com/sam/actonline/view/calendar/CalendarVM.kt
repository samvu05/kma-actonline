package com.sam.actonline.view.calendar

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applandeo.materialcalendarview.EventDay
import com.sam.actonline.R
import com.sam.actonline.model.event.BaseEvent
import com.sam.actonline.model.event.ItemEvent
import com.sam.actonline.network.MoodleService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import java.util.*
import javax.inject.Inject

/**
 * Created by Dinh Sam Vu on 5/25/2021.
 */

@HiltViewModel
class CalendarVM @Inject constructor(
    private val moodleService: MoodleService
) :
    ViewModel() {

    val dateEvents = MutableLiveData<List<ItemEvent>>()
    val monthEvents = MutableLiveData<MutableList<EventDay>>()

    fun getEventByDate(year: Int, month: Int, date: Int) {
        viewModelScope.launch {
            val response = moodleService.getEventDayView(year, month, date)
            if (response.isSuccessful) {
                dateEvents.postValue(response.body()!!.events)
            } else {
                Log.d("logBD", "PINGGG")
            }
        }
    }

    fun getEventByMonth(year: Int, month: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val listEvent = mutableListOf<EventDay>()
            val listJob = mutableListOf<Deferred<Response<BaseEvent>>>()
            for (i in 1..31) {
                val job = async {
                    moodleService.getEventDayView(year, month, i)
                }
                listJob.add(job)
            }
            val results: List<Response<BaseEvent>> = listJob.awaitAll()
            for (i in 0..results.size - 1) {
                if (results[i].isSuccessful && !results[i].body()?.events.isNullOrEmpty()) {
                    val calendar = Calendar.getInstance().apply {
                        set(year, month - 1, i + 1)
                    }
                    listEvent.add(
                        EventDay(
                            calendar,
                            R.drawable.ic_tv_startdate,
                            Color.parseColor("#228B22")
                        )
                    )
                }
            }
            monthEvents.postValue(listEvent)
        }
    }
}