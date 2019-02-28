package com.powerincode.questionnaire_app.screens.auth.signin

import android.support.annotation.StringRes
import com.google.android.gms.auth.api.signin.GoogleSignInClient

/**
 * Created by powerman23rus on 27/02/2019.
 */

sealed class SignInState {
    class GoogleSignInState(val client : GoogleSignInClient) : SignInState()
    class ErrorState(val errors : List<SignInError>) : SignInState()
    object ClearErrorsState : SignInState()
    object SignInCompleteState : SignInState()
}

sealed class SignInError {
    class EmailError(@StringRes val messageId : Int) : SignInError()
    class PasswordError(@StringRes val messageId : Int) : SignInError()
    class AuthError(@StringRes val messageId : Int) : SignInError()
}

sealed class SignInNavigation {

}