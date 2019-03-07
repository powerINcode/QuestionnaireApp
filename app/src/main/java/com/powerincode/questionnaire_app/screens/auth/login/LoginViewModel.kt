package com.powerincode.questionnaire_app.screens.auth.login

import com.powerincode.questionnaire_app.domain.uscases.BaseUseCase.None
import com.powerincode.questionnaire_app.domain.uscases.profile.GetProfileUseCase
import com.powerincode.questionnaire_app.screens._base.viewmodel.StateViewModel
import javax.inject.Inject

/**
 * Created by powerman23rus on 25/02/2019.
 */
class LoginViewModel @Inject constructor(private val getProfile : GetProfileUseCase) : StateViewModel<LoginViewState, LoginNavigation>() {

    init {
        request {
            getProfile(None())?.let {
               _navigation.event = LoginNavigation.NavigateToMain
            }
        }
    }

    fun onSignInClick() {
        _navigation.event = LoginNavigation.NavigateToSignIn
    }

    fun onSignUpClick() {
        _navigation.event = LoginNavigation.NavigateToSignUp
    }
}