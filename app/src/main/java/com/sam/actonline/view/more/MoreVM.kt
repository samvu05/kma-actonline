package com.sam.actonline.view.more

import androidx.lifecycle.ViewModel
import com.sam.actonline.network.MoodleService
import com.sam.actonline.utils.PrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//
// Created by Dinh Sam Vu on July 03, 2021.
//

@HiltViewModel
class MoreVM @Inject constructor(
    private val prefHelper: PrefHelper,
) : ViewModel() {

    fun logOut() {
        prefHelper.logout()
    }


}