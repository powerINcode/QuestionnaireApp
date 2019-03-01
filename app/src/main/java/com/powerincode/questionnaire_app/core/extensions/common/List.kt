package com.powerincode.questionnaire_app.core.extensions.common

/**
 * Created by powerman23rus on 01/03/2019.
 */

fun <T> List<T>.firstIfExist(block : (T) -> Unit) = firstOrNull()?.let(block)