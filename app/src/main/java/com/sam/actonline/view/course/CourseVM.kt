package com.sam.actonline.view.course

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.actonline.database.MyDatabase
import com.sam.actonline.model.ItemCourse
import com.sam.actonline.model.coursedetail.ItemSection
import com.sam.actonline.network.MoodleService
import com.sam.actonline.utils.PrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Dinh Sam Vu on 5/12/2021.
 */

@HiltViewModel
class CourseVM @Inject constructor(
    private val moodleService: MoodleService,
    private val prefHelper: PrefHelper,
    private val myDatabase: MyDatabase
) : ViewModel() {
    val listCourse = MutableLiveData<MutableList<ItemCourse>>()
    val courseContent = MutableLiveData<List<ItemSection>>()

    fun getListCourse() {
        viewModelScope.launch {
            val response = moodleService.getCourses(prefHelper.userID)
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                listCourse.value = response.body() as MutableList<ItemCourse>
            } else Unit
        }
    }

    fun getCourseDetail(courseID: Int) {
        viewModelScope.launch {
            val response = moodleService.getCourseContent(courseID)
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                courseContent.value = response.body()!!
            } else Unit
        }
    }

}