package com.powerincode.questionnaire_app.domain.auth.common.usecase

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.MatchRegexRule
import com.powerincode.questionnaire_app.core.validators.rules.NotEmptyRule
import com.powerincode.questionnaire_app.domain.BaseValidationUseCase

/**
 * Created by powerman23rus on 04/03/2019.
 */
class ValidateEmailUseCase : BaseValidationUseCase<String>() {
    override fun execute(item : String?) : List<RuleError> {
        return applyRules(
            NotEmptyRule(item, R.string.error_all_email_empty),
            MatchRegexRule(item, R.string.error_all_email_format_incorrect)
        )
    }
}