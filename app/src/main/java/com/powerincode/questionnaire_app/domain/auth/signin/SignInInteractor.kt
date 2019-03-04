package com.powerincode.questionnaire_app.domain.auth.signin

import com.powerincode.questionnaire_app.core.validators.errors.RuleError

interface SignInInteractor {
    fun validateEmail(email : String?) : List<RuleError>
    fun validatePassword(password : String?) : List<RuleError>

    suspend fun signIn(
        email : String?,
        password : String?
    ) : SignInResult
}