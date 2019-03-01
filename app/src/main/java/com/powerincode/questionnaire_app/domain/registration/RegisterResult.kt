package com.powerincode.questionnaire_app.domain.registration

import com.powerincode.questionnaire_app.core.validators.errors.RuleError

sealed class RegisterResult(val errors : List<RuleError>) {
    val firstMessageIdOrNull : Int? = errors.firstOrNull()?.messageId

    class NameError(errors : List<RuleError>) : RegisterResult(errors)
    class EmailError(errors : List<RuleError>) : RegisterResult(errors)
    class PasswordError(errors : List<RuleError>) : RegisterResult(errors)
    class PasswordEqualityError(errors : List<RuleError>) : RegisterResult(errors)
    object UserNotCreatedError : RegisterResult(emptyList())
    object Success : RegisterResult(emptyList())
}