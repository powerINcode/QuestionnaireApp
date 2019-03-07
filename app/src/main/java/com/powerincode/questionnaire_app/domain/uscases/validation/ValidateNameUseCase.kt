package com.powerincode.questionnaire_app.domain.uscases.validation

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.NotEmptyRule
import javax.inject.Inject

/**
 * Created by powerman23rus on 04/03/2019.
 */
class ValidateNameUseCase @Inject constructor() : BaseValidationUseCase<String?>() {
    override suspend fun run(param : String?) : List<RuleError> {
        return applyRules(
            NotEmptyRule(param, R.string.error_validation_name_empty)
        )
    }
}