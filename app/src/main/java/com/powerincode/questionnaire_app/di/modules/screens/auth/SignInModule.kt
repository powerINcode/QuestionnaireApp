package com.powerincode.questionnaire_app.di.modules.screens.auth

import android.arch.lifecycle.ViewModel
import com.powerincode.questionnaire_app.di.keys.ViewModelKey
import com.powerincode.questionnaire_app.screens.auth.signin.interactor.SignInInteractor
import com.powerincode.questionnaire_app.screens.auth.signin.interactor.SignInInteractorImpl
import com.powerincode.questionnaire_app.screens.auth.signin.viewmodel.SignInViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by powerman23rus on 04/03/2019.
 */
@Module
abstract class SignInModule {
    @Binds
    abstract fun provideSignInInteractorUseCase(interactor : SignInInteractorImpl) : SignInInteractor

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindSignInViewModel(model : SignInViewModel) : ViewModel
}