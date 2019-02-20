package com.powerincode.questionnaire_app.core.livedata

/**
 * Created by powerman23rus on 20/02/2019.
 */
class Event<T>(val data : T?) {
    var isHandled : Boolean = false
        private set

    fun getDataIfNotHandled() : T? {
        return if (isHandled) {
            null
        } else {
            isHandled = true
            data
        }
    }

}