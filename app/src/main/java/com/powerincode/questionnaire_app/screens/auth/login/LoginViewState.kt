package com.powerincode.questionnaire_app.screens.auth.login

import android.support.annotation.StringRes
import com.google.android.gms.auth.api.credentials.CredentialsClient

/**
 * Created by powerman23rus on 25/02/2019.
 */
sealed class LoginViewState {
    class ShowLoginState(val client : CredentialsClient) : LoginViewState()
    object SignUpReady : LoginViewState()
    object SignUpNotReady : LoginViewState()
    class SignUpValidationError(@StringRes val messageId : Int) : LoginViewState()
}

sealed class LoginNavigation {
    object NavigateToMain : LoginNavigation()
}