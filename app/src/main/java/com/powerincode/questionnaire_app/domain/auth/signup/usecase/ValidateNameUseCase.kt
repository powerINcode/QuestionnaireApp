package com.powerincode.questionnaire_app.domain.auth.signup.usecase

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.NotEmptyRule
import com.powerincode.questionnaire_app.domain.BaseValidationUseCase

/**
 * Created by powerman23rus on 04/03/2019.
 */
class ValidateNameUseCase : BaseValidationUseCase<String>() {
    override fun execute(item : String?) : List<RuleError> {
        return applyRules(
            NotEmptyRule(item, R.string.error_all_name_empty)
        )
    }
}