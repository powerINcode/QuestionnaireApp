package com.powerincode.questionnaire_app.di.modules

import android.arch.lifecycle.ViewModel
import com.powerincode.questionnaire_app.di.keys.ViewModelKey
import com.powerincode.questionnaire_app.domain.registration.RegistrationUseCase
import com.powerincode.questionnaire_app.domain.registration.RegistrationUseCaseImpl
import com.powerincode.questionnaire_app.screens.auth.login.LoginViewModel
import com.powerincode.questionnaire_app.screens.auth.signin.SignInViewModel
import com.powerincode.questionnaire_app.screens.auth.signup.SignUpViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by powerman23rus on 25/02/2019.
 */

@Module
abstract class AuthModule {
    @Binds
    abstract fun provideRegistrationUseCase(useCase : RegistrationUseCaseImpl) : RegistrationUseCase

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(model : LoginViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindSignInViewModel(model : SignInViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(model : SignUpViewModel) : ViewModel
}