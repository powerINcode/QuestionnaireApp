package com.powerincode.questionnaire_app.di.modules.auth

import com.google.firebase.auth.FirebaseAuth
import com.powerincode.questionnaire_app.domain.auth.common.usecase.ValidateEmailUseCase
import com.powerincode.questionnaire_app.domain.auth.common.usecase.ValidatePasswordUseCase
import com.powerincode.questionnaire_app.domain.auth.signup.SignUpInteractor
import com.powerincode.questionnaire_app.domain.auth.signup.SignUpInteractorImpl
import com.powerincode.questionnaire_app.domain.auth.signup.usecase.ValidateNameUseCase
import dagger.Module
import dagger.Provides

/**
 * Created by powerman23rus on 04/03/2019.
 */
@Module
object SignUpModule {
    @JvmStatic
    @Provides
    fun provideValidateNameUseCase() = ValidateNameUseCase()

    @JvmStatic
    @Provides
    fun provideSignUpInteractorUseCase(
        validateNameUseCase : ValidateNameUseCase,
        validateEmailUseCase : ValidateEmailUseCase,
        validatePasswordUseCase : ValidatePasswordUseCase,
        firebaseAuth : FirebaseAuth
    ) : SignUpInteractor =
        SignUpInteractorImpl(validateEmailUseCase, validateNameUseCase, validatePasswordUseCase, firebaseAuth)
}