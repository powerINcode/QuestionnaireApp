package com.powerincode.questionnaire_app.core.validators.rules

import android.support.annotation.StringRes
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.base.BaseValidatorRule

/**
 * Created by powerman23rus on 28/02/2019.
 */
class EqualityRule<T>(val first : T?, val second : T?, @StringRes messageId : Int) : BaseValidatorRule<Int>(messageId) {
    override fun validate() : RuleError? {
        return if (first == second) {
            null
        } else {
            RuleError(messageId)
        }
    }
}