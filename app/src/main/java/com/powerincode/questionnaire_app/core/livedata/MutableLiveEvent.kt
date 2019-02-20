package com.powerincode.questionnaire_app.core.livedata

/**
 * Created by powerman23rus on 20/02/2019.
 */
class MutableLiveEvent<T> : LiveEvent<T>() {
    var event : T?
        get() = value?.data
        set(value) = setValue(Event(value))

    fun postEvent(value : T) {
        super.postValue(Event(value))
    }
}