package com.powerincode.questionnaire_app.screens.auth.login

import com.google.android.gms.auth.api.signin.GoogleSignInClient

/**
 * Created by powerman23rus on 25/02/2019.
 */
sealed class LoginViewState {
    class ShowLoginState(val client : GoogleSignInClient) : LoginViewState()
}

sealed class LoginNavigation {
    object NavigateToMain : LoginNavigation()
}