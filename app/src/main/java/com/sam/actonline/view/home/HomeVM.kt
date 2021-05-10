package com.sam.actonline.view.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.actonline.database.MyDatabase
import com.sam.actonline.model.Site
import com.sam.actonline.network.MoodleService
import com.sam.actonline.utils.PrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Dinh Sam Vu on 4/18/2021.
 */

@HiltViewModel
class HomeVM @Inject constructor(
    private val database: MyDatabase,
    private val prefHelper: PrefHelper,
    private val moodleService: MoodleService
) :
    ViewModel() {
    val site = MutableLiveData<Site>()

    init {
        if (prefHelper.checkToken) {
            getSite()
        }
    }

    fun getSite() {
        viewModelScope.launch {
            val response = moodleService.getSite()
            if (response.isSuccessful && response.body()?.errorcode.isNullOrBlank()) {
                val result = response.body()!!
                site.value = result
                database.siteDao().insert(result)
                prefHelper.userID = result.userId
            } else {
                Log.d("logDB", response.body()?.errorcode!!)
            }
        }
    }

    fun logout() {

    }


}