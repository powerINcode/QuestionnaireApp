package com.powerincode.questionnaire_app.screens.auth.signin

import com.google.android.gms.auth.api.signin.GoogleSignInClient

/**
 * Created by powerman23rus on 27/02/2019.
 */

sealed class SignInState {
    class GoogleSignInState(val client : GoogleSignInClient) : SignInState()
    object SignInCompleteState : SignInState()
}

sealed class SignInNavigation {

}