package com.powerincode.questionnaire_app.screens.auth.signup

import android.support.annotation.StringRes

/**
 * Created by powerman23rus on 28/02/2019.
 */

sealed class SignUpState {
//    class PasswordEqualityState(val isPasswordEqual : Boolean?, val isConfirmPasswordEqual : Boolean?) : SignUpState()
//    class ErrorState(val errors : List<SignUpError>) : SignUpState()
//    object ClearErrorsState : SignUpState()
}

sealed class SignUpError {
    class NameError(@StringRes val messageId : Int) : SignUpError()
    class EmailError(@StringRes val messageId : Int) : SignUpError()
    class PasswordError(@StringRes val messageId : Int) : SignUpError()
    class PasswordEqualityError(@StringRes val messageId : Int) : SignUpError()
}

sealed class SignUpNavigation {

}