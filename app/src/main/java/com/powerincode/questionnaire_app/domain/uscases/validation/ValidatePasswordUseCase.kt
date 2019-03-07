package com.powerincode.questionnaire_app.domain.uscases.validation

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.EqualOrMoreThenRule
import com.powerincode.questionnaire_app.core.validators.rules.EqualityRule
import com.powerincode.questionnaire_app.core.validators.rules.NotEmptyRule
import javax.inject.Inject

/**
 * Created by powerman23rus on 04/03/2019.
 */
class ValidatePasswordUseCase @Inject constructor() : BaseValidationUseCase<String?>() {
    override suspend fun run(param : String?) : List<RuleError> {
        return applyRules(
            NotEmptyRule(param, R.string.error_validation_password_empty),
            EqualOrMoreThenRule(param?.length, 6, R.string.error_validation_password_incorrect)
        )
    }

    fun validateEquality(password : String?, confirmPassword : String?) : List<RuleError> {
        return applyRules(EqualityRule(password, confirmPassword, R.string.error_signin_password_not_equals))
    }
}