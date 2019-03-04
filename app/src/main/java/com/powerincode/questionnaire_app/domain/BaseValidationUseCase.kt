package com.powerincode.questionnaire_app.domain

import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.base.ValidatorRule

/**
 * Created by powerman23rus on 04/03/2019.
 */
abstract class BaseValidationUseCase<T> : UseCase {
    abstract fun execute(item: T?) : List<RuleError>

    protected fun applyRules(vararg rules : ValidatorRule) : List<RuleError> {
        return rules.mapNotNull { it.validate() }
    }
}