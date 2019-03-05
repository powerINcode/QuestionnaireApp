package com.powerincode.questionnaire_app.core.extensions.common

import com.powerincode.questionnaire_app.core.validators.errors.RuleError

/**
 * Created by powerman23rus on 01/03/2019.
 */

fun <T> List<T>.firstIfExist(block : (T) -> Unit) = firstOrNull()?.let(block)
fun List<RuleError>.errorIdOrNull() : Int? = firstOrNull()?.messageId