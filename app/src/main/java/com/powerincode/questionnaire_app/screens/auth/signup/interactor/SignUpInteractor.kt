package com.powerincode.questionnaire_app.screens.auth.signup.interactor

import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.domain.uscases.auth.SignUpUseCase

interface SignUpInteractor {
    fun validateName(name : String?) : List<RuleError>
    fun validateEmail(email : String?) : List<RuleError>
    fun validatePassword(password : String?) : List<RuleError>
    fun validateConfirmPassword(password : String?) : List<RuleError>
    fun validatePasswordsEquality(password : String?, confirmPassword : String?) : List<RuleError>

    suspend fun register(
        name : String?,
        email : String?,
        password : String?,
        confirmPassword : String?
    ) : SignUpUseCase.SignUpResult
}