package com.powerincode.questionnaire_app.domain.registration

import com.powerincode.questionnaire_app.core.validators.errors.RuleError

/**
 * Created by powerman23rus on 01/03/2019.
 */
interface RegistrationUseCase {
    fun validateName(name : String?) : List<RuleError>
    fun validateEmail(email : String?) : List<RuleError>
    fun validatePassword(password : String?) : List<RuleError>
    fun validatePasswordEquality(password : String?, confirmPassword : String?) : List<RuleError>

    suspend fun register(name : String?, email : String?, password : String?, confirmPassword : String?) : RegisterResult
}