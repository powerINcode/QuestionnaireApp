package com.powerincode.questionnaire_app.core.validators.rules

import android.support.annotation.StringRes
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.base.BaseValidatorRule

/**
 * Created by powerman23rus on 28/02/2019.
 */
class NotEmptyRule(val item : String?, @StringRes messageId : Int) : BaseValidatorRule<String?>(messageId) {
    override fun validate() : RuleError? {
        return if (!item.isNullOrEmpty()) {
            null
        } else {
            RuleError(messageId)
        }
    }
}