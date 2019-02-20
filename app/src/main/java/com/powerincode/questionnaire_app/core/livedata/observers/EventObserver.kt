package com.powerincode.questionnaire_app.core.livedata.observers

import android.arch.lifecycle.Observer
import com.powerincode.questionnaire_app.core.livedata.Event

/**
 * Created by powerman23rus on 20/02/2019.
 */
class EventObserver<T>(private val block : (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event : Event<T>?) {
        event?.getDataIfNotHandled()?.let { block(it) }
    }
}