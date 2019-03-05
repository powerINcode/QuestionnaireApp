package com.powerincode.questionnaire_app.domain.uscases.validation

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.MatchRegexRule
import com.powerincode.questionnaire_app.core.validators.rules.NotEmptyRule
import javax.inject.Inject

/**
 * Created by powerman23rus on 04/03/2019.
 */
class ValidateEmailUseCase @Inject constructor() : BaseValidationUseCase<String>() {
    override fun execute(item : String?) : List<RuleError> {
        return applyRules(
            NotEmptyRule(item, R.string.error_validation_email_empty),
            MatchRegexRule(item, R.string.error_validation_email_format_incorrect)
        )
    }
}