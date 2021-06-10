package com.sam.actonline.view.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.actonline.model.Site
import com.sam.actonline.model.event.ItemEvent
import com.sam.actonline.network.MoodleService
import com.sam.actonline.utils.PrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Dinh Sam Vu on 4/18/2021.
 */

@HiltViewModel
class HomeVM @Inject constructor(
    private val prefHelper: PrefHelper,
    private val moodleService: MoodleService
) :
    ViewModel() {
    val site = MutableLiveData<Site>()

    //    val error = MutableLiveData<RestError>()
    val listEvent = MutableLiveData<List<ItemEvent>>()

    init {
        if (prefHelper.checkToken) {
            getSite()
            getUpcomingEvent()

        } else {
//            error.value = RestError.TOKEN_ERROR
        }
    }

    private fun getUpcomingEvent() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = moodleService.getUpcomingEvent()
            if (response.isSuccessful) {
                listEvent.postValue(response.body()!!.events)
            }
        }
    }

    fun getSite() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = moodleService.getSite()
            if (response.isSuccessful && response.body()?.errorcode.isNullOrBlank()) {
                val result = response.body()!!
                prefHelper.userID = result.userId
                site.postValue(result)
            } else {
//                error.value = RestError.REQUEST_ERROR
            }
        }
    }
}