package com.powerincode.questionnaire_app.screens.auth.signup.interactor

import com.powerincode.questionnaire_app.domain.uscases.auth.SignUpUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateEmailUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateNameUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidatePasswordUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 04/03/2019.
 */
class SignUpInteractorImpl @Inject constructor(private val validateEmailUseCase : ValidateEmailUseCase,
                                               private val validateNameUseCase : ValidateNameUseCase,
                                               private val validatePasswordUseCase : ValidatePasswordUseCase,
                                               private val signUpUseCase : SignUpUseCase) :
    SignUpInteractor {

    override fun validateName(name : String?) = validateNameUseCase.block(name)
    override fun validateEmail(email : String?) = validateEmailUseCase.block(email)
    override fun validatePassword(password : String?) = validatePasswordUseCase.block(password)
    override fun validateConfirmPassword(password : String?) = validatePasswordUseCase.block(password)
    override fun validatePasswordsEquality(password : String?, confirmPassword : String?) = validatePasswordUseCase.validateEquality(password, confirmPassword)

    override suspend fun register(
        name : String?,
        email : String?,
        password : String?,
        confirmPassword : String?
    ) : SignUpUseCase.SignUpResult {
        return signUpUseCase.execute(name, email, password, confirmPassword)
    }

}