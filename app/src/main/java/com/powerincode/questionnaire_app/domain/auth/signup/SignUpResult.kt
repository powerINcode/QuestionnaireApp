package com.powerincode.questionnaire_app.domain.auth.signup

import com.powerincode.questionnaire_app.core.validators.errors.RuleError

sealed class SignUpResult(val errors : List<RuleError>) {
        val firstMessageIdOrNull : Int? = errors.firstOrNull()?.messageId

        class NameError(errors : List<RuleError>) : SignUpResult(errors)
        class EmailError(errors : List<RuleError>) : SignUpResult(errors)
        class PasswordError(errors : List<RuleError>) : SignUpResult(errors)
        class PasswordEqualityError(errors : List<RuleError>) : SignUpResult(errors)
        object UserNotCreatedError : SignUpResult(emptyList())
        object Success : SignUpResult(emptyList())
    }