package com.sam.actonline.view.login

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.actonline.network.MoodleService
import com.sam.actonline.utils.PrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Dinh Sam Vu on 4/15/2021.
 */

@HiltViewModel
class LoginVM @Inject constructor(
    private val moodleService: MoodleService,
    private val preferencesHelper: PrefHelper,
) : ViewModel() {

    var logged = MutableLiveData<Boolean>()

    fun login(userName: String, pass: String) {
        viewModelScope.launch {
            try {
                val response = moodleService.login(userName, pass)
                if (response.isSuccessful) {
                    response.body()!!.apply {
                        if (!token.isNullOrEmpty()) {
                            preferencesHelper.token = token
                            logged.value = true
                        } else {
                            logged.value = false
                        }
                    }
                } else logged.value = false
            } catch (e: Exception) {
                logged.value = false
                Log.d("logDB", e.toString())
            }
        }
    }

}