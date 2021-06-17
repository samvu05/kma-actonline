package com.sam.actonline.view.newfeeds

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.actonline.model.coursedetail.Module
import com.sam.actonline.network.MoodleService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Dinh Sam Vu on 6/12/2021.
 */
@HiltViewModel
class NewsFeedVM @Inject constructor(
    private val moodleService: MoodleService,
) : ViewModel() {
    val listModule = MutableLiveData<List<Module>>()

    //    Newsfeed content is course whose id == 1
    fun getCourseDetail(courseID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = moodleService.getCourseContent(courseID)
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                val listModule = mutableListOf<Module>()
                response.body()!!.forEach {
                    if (!it.modules.isNullOrEmpty()) {
                        listModule.addAll(it.modules)
                    }
                }
                this@NewsFeedVM.listModule.postValue(listModule)
            } else Unit
        }
    }

}