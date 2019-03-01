package com.powerincode.questionnaire_app.core.validators.rules.base

import com.powerincode.questionnaire_app.core.validators.errors.RuleError

/**
 * Created by powerman23rus on 28/02/2019.
 */
interface ValidatorRule {
    val messageId: Int
    fun validate() : RuleError?
}