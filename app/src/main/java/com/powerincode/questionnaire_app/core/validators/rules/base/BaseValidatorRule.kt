package com.powerincode.questionnaire_app.core.validators.rules.base

import android.support.annotation.StringRes

/**
 * Created by powerman23rus on 28/02/2019.
 */
abstract class BaseValidatorRule<T>(@StringRes override val messageId : Int) :
    ValidatorRule {

}