package com.powerincode.questionnaire_app.screens.auth.login

/**
 * Created by powerman23rus on 25/02/2019.
 */
sealed class LoginViewState {

}

sealed class LoginNavigation {
    object NavigateToSignIn : LoginNavigation()
    object NavigateToSignUp : LoginNavigation()
}