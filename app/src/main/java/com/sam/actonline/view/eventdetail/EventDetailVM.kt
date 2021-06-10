package com.sam.actonline.view.eventdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.actonline.model.event.ItemEventDetail
import com.sam.actonline.network.MoodleService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Dinh Sam Vu on 5/26/2021.
 */

@HiltViewModel
class EventDetailVM @Inject constructor(
    private val moodleService: MoodleService
) : ViewModel() {

    val eventDetail = MutableLiveData<ItemEventDetail>()

    fun getEventDetail(eventID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = moodleService.getEventByID(eventID)
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) { eventDetail.value = response.body()!!.event }
            }
        }
    }

}