package com.powerincode.questionnaire_app.domain.auth.signup

import com.powerincode.questionnaire_app.core.validators.errors.RuleError

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
    ) : SignUpResult
}