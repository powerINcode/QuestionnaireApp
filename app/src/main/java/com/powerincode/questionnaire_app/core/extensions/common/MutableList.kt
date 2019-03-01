package com.powerincode.questionnaire_app.core.extensions.common

/**
 * Created by powerman23rus on 28/02/2019.
 */

fun <T> MutableList<T>.replaceIfExist(item : T, filter : (T) -> Boolean) {
    firstOrNull(filter)?.let {
        remove(it)
    }

    add(item)
}