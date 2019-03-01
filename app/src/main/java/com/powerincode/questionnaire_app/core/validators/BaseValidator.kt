package com.powerincode.questionnaire_app.core.validators

import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.base.ValidatorRule

/**
 * Created by powerman23rus on 28/02/2019.
 */
abstract class BaseValidator<T> : Validator<T> {
    protected fun applyRules(vararg rules : ValidatorRule) : List<RuleError> {
        return rules.mapNotNull { it.validate() }
    }
}