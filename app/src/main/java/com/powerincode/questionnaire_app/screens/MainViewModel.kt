package com.powerincode.questionnaire_app.screens

import com.powerincode.questionnaire_app.data.preference.PreferenceProvider
import com.powerincode.questionnaire_app.data.preference.api.FakeNetwork
import com.powerincode.questionnaire_app.screens.base.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by powerman23rus on 12/02/2019.
 */
class MainViewModel @Inject constructor(private val preferenceProvider : PreferenceProvider) : BaseViewModel<Any?>() {


    init {
        request {
            val data = withContext(Dispatchers.IO) { FakeNetwork.getData() }
            if (data) {
                _message.event = "Success"
            } else {
                throw IllegalAccessException()
            }
        }
    }
}