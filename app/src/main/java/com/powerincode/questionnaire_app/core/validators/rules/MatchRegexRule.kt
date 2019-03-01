package com.powerincode.questionnaire_app.core.validators.rules

import android.support.annotation.StringRes
import android.util.Patterns
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.base.BaseValidatorRule

/**
 * Created by powerman23rus on 28/02/2019.
 */
class MatchRegexRule(val item : String?, @StringRes messageId : Int) : BaseValidatorRule<String?>(messageId) {
    override fun validate() : RuleError? {
        return if (item != null && Patterns.EMAIL_ADDRESS.toRegex().containsMatchIn(item)) {
            null
        } else {
            RuleError(messageId)
        }
    }
}