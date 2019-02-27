package com.powerincode.questionnaire_app.screens.auth.signin

import android.support.annotation.StringRes

/**
 * Created by powerman23rus on 27/02/2019.
 */

sealed class SignInState {
    class Error(val errors : List<SignInError>) : SignInState()
    object ClearErrors : SignInState()
}

sealed class SignInError {
    class EmailError(@StringRes val messageId : Int) : SignInError()
    class PasswordError(@StringRes val messageId : Int) : SignInError()
    class AuthError(@StringRes val messageId : Int) : SignInError()
}

sealed class SignInNavigation {

}