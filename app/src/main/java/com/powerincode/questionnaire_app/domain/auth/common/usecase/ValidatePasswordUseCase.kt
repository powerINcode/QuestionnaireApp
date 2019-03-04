package com.powerincode.questionnaire_app.domain.auth.common.usecase

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.EqualOrMoreThenRule
import com.powerincode.questionnaire_app.core.validators.rules.EqualityRule
import com.powerincode.questionnaire_app.core.validators.rules.NotEmptyRule
import com.powerincode.questionnaire_app.domain.BaseValidationUseCase

/**
 * Created by powerman23rus on 04/03/2019.
 */
class ValidatePasswordUseCase : BaseValidationUseCase<String>() {
    override fun execute(item : String?) : List<RuleError> {
        return applyRules(
            NotEmptyRule(item, R.string.error_all_password_empty),
            EqualOrMoreThenRule(item?.length, 6, R.string.error_all_password_incorrect)
        )
    }

    fun validateEquality(password : String? , confirmPassword : String?) : List<RuleError> {
        return applyRules(EqualityRule(password, confirmPassword, R.string.error_signin_password_not_equals))
    }
}