package com.powerincode.questionnaire_app.screens._base.viewmodel

import android.arch.lifecycle.ViewModel
import com.powerincode.questionnaire_app.core.livedata.LiveEvent
import com.powerincode.questionnaire_app.core.livedata.MutableLiveEvent
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by powerman23rus on 12/02/2019.
 */
abstract class BaseViewModel : ViewModel(), CoroutineScope {
    private val job : Job = Job()
    override val coroutineContext : CoroutineContext = Dispatchers.Main + job


    protected val _message : MutableLiveEvent<String> = MutableLiveEvent()
    val message : LiveEvent<String> = _message

    protected val _loader : MutableLiveEvent<Boolean> = MutableLiveEvent()
    val loader : LiveEvent<Boolean> = _loader

    override fun onCleared() {
        super.onCleared()
        cancel()
    }


    protected fun request(block : suspend () -> Unit) = launch {
        try {
            _loader.event = true
            block()
        } catch (e : Exception) {
            if(e is CancellationException) throw e
            _message.event = e.toString()
        } finally {
            _loader.event = false
        }
    }
}