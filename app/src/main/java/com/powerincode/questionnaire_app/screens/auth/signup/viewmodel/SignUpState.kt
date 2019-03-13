package com.powerincode.questionnaire_app.screens.auth.signup.viewmodel

import com.google.android.gms.auth.api.credentials.CredentialsClient
import com.google.android.gms.auth.api.credentials.HintRequest

/**
 * Created by powerman23rus on 28/02/2019.
 */

sealed class SignUpState {
    object ClearState : SignUpState()
    class CredentialHints(val credentialClient : CredentialsClient, val  hintRequest : HintRequest) : SignUpState()
//    class PasswordEqualityState(val isPasswordEqual : Boolean?, val isConfirmPasswordEqual : Boolean?) : SignUpState()
//    class ErrorState(val errors : List<SignUpError>) : SignUpState()
//    object ClearErrorsState : SignUpState()
}

//sealed class SignUpError {
//    class NameError(@StringRes val messageId : Int) : SignUpError()
//    class EmailError(@StringRes val messageId : Int) : SignUpError()
//    class PasswordError(@StringRes val messageId : Int) : SignUpError()
//    class PasswordEqualityError(@StringRes val messageId : Int) : SignUpError()
//}

sealed class SignUpNavigation {
    object NavigateToSignIn :  SignUpNavigation()
}