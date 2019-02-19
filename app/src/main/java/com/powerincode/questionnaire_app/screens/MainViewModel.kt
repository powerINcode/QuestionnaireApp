package com.powerincode.questionnaire_app.screens

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.powerincode.questionnaire_app.data.preference.PreferenceProvider
import com.powerincode.questionnaire_app.screens.base.viewmodel.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * Created by powerman23rus on 12/02/2019.
 */
class MainViewModel @Inject constructor(private val preferenceProvider : PreferenceProvider) : BaseViewModel() {
    private val message : MutableLiveData<String> = MutableLiveData()
    fun getMessage() : LiveData<String> = message

    private var isCreated = false
    fun onCreated() {
        if (!isCreated) {
            isCreated = true

            runBlocking {
                delay(1000)
                message.postValue(preferenceProvider.token)
            }
        }
    }
}