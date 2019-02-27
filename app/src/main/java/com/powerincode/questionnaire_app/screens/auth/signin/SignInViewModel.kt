package com.powerincode.questionnaire_app.screens.auth.signin

import com.powerincode.questionnaire_app.screens._base.viewmodel.StateViewModel
import javax.inject.Inject

/**
 * Created by powerman23rus on 27/02/2019.
 */
class SignInViewModel @Inject constructor() : StateViewModel<SignInState, SignInNavigation>() {
    val email : String = ""
    val password : String = ""

    fun onSignInClick() {

    }

    fun onGoogleSignInClick() {

    }

    fun onSignUpClick() {

    }
}