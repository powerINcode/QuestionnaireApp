package com.powerincode.questionnaire_app.core.validators

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.NotEmptyRule

/**
 * Created by powerman23rus on 28/02/2019.
 */
class NameValidator : BaseValidator<String?>() {
    override fun validate(item : String?) : List<RuleError> {
        return applyRules(
            NotEmptyRule(item, R.string.error_all_name_empty)
        )
    }
}