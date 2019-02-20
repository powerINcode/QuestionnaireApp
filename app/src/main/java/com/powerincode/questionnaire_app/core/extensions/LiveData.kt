package com.powerincode.questionnaire_app.core.extensions

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import com.powerincode.questionnaire_app.core.livedata.Event
import com.powerincode.questionnaire_app.core.livedata.observers.EventObserver

/**
 * Created by powerman23rus on 20/02/2019.
 */

fun <T> LiveData<Event<T>>.observeEvent(owner : LifecycleOwner, block : (T) -> Unit) {
    observe(owner, EventObserver(block))
}