package com.powerincode.questionnaire_app.domain.auth.signin

import com.google.firebase.auth.FirebaseUser
import com.powerincode.questionnaire_app.core.validators.errors.RuleError

sealed class SignInResult(val errors : List<RuleError>) {
        val firstMessageIdOrNull : Int? = errors.firstOrNull()?.messageId

        class EmailError(errors : List<RuleError>) : SignInResult(errors)
        class PasswordError(errors : List<RuleError>) : SignInResult(errors)
        class UserNotSignInError(errors : List<RuleError>) : SignInResult(errors)
        class Success(val user : FirebaseUser) : SignInResult(emptyList())
    }