package com.powerincode.questionnaire_app.core.validators

import com.powerincode.questionnaire_app.core.validators.errors.RuleError

/**
 * Created by powerman23rus on 28/02/2019.
 */
interface Validator<T> {
    fun validate(item : T) : List<RuleError>
}