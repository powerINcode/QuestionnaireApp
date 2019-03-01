package com.powerincode.questionnaire_app.core.validators.rules

import android.support.annotation.StringRes
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.base.BaseValidatorRule

/**
 * Created by powerman23rus on 28/02/2019.
 */
class EqualOrMoreThenRule(val item : Int?, val value : Int, @StringRes messageId : Int) : BaseValidatorRule<Int>(messageId) {
    override fun validate() : RuleError? {
        return if (item != null && item >= value) {
            null
        } else {
            RuleError(messageId)
        }
    }
}