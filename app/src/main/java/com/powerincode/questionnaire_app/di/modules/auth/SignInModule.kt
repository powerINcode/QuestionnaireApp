package com.powerincode.questionnaire_app.di.modules.auth

import com.powerincode.questionnaire_app.domain.auth.signin.SignInInteractor
import com.powerincode.questionnaire_app.domain.auth.signin.SignInInteractorImpl
import dagger.Binds
import dagger.Module

/**
 * Created by powerman23rus on 04/03/2019.
 */
@Module
abstract class SignInModule {
    @Binds
    abstract fun provideSignInInteractorUseCase(interactor : SignInInteractorImpl) : SignInInteractor
}