package com.sam.actonline.view.badges

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.actonline.model.ItemBadge
import com.sam.actonline.network.MoodleService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Dinh Sam Vu on 6/12/2021.
 */
@HiltViewModel
class BadgesVM @Inject constructor(
    private val moodleService: MoodleService,
) : ViewModel() {
    val listBadges = MutableLiveData<List<ItemBadge>>()

    fun getListBadges() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = moodleService.getListBadges()
            if (response.isSuccessful && !response.body()?.badges.isNullOrEmpty()) {
                listBadges.postValue(response.body()!!.badges)
            } else Unit
        }
    }

}