package com.powerincode.questionnaire_app.core.validators

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.MatchRegexRule
import com.powerincode.questionnaire_app.core.validators.rules.NotEmptyRule

/**
 * Created by powerman23rus on 28/02/2019.
 */
class EmailValidator : BaseValidator<String?>() {
    override fun validate(item : String?) : List<RuleError> = applyRules(
        NotEmptyRule(item, R.string.error_all_email_empty),
        MatchRegexRule(item, R.string.error_all_email_format_incorrect)
    )
}