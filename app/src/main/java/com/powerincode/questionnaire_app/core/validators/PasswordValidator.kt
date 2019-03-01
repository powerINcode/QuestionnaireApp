package com.powerincode.questionnaire_app.core.validators

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.EqualOrMoreThenRule
import com.powerincode.questionnaire_app.core.validators.rules.EqualityRule
import com.powerincode.questionnaire_app.core.validators.rules.NotEmptyRule

/**
 * Created by powerman23rus on 28/02/2019.
 */
class PasswordValidator : BaseValidator<String?>() {
    override fun validate(item : String?) : List<RuleError> {
        return applyRules(
            NotEmptyRule(item, R.string.error_all_password_empty),
            EqualOrMoreThenRule(item?.length, 6, R.string.error_all_password_incorrect)
        )
    }

    fun validateEquality(password : String? , confirmPassword : String?) : List<RuleError> {
        return applyRules(EqualityRule(password, confirmPassword, R.string.error_signin_password_not_equals))
    }
}