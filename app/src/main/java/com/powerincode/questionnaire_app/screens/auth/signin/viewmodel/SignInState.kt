package com.powerincode.questionnaire_app.screens.auth.signin.viewmodel

import com.google.android.gms.auth.api.credentials.CredentialsClient
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ResolvableApiException
import com.powerincode.questionnaire_app.data.realtimedatabase.models.user.UserModel

/**
 * Created by powerman23rus on 27/02/2019.
 */

sealed class SignInState {
    object ClearState : SignInState()
    class GoogleSignInState(val client : GoogleSignInClient) : SignInState()
    class CredentialHints(val credentialClient : CredentialsClient, val  hintRequest : HintRequest) : SignInState()
    class CredentialChooseProfile(val resolveException : ResolvableApiException) : SignInState()
    class CredentialSavePromptState(val user : UserModel, val resolveException : ResolvableApiException) : SignInState()
    object SignInCompleteState : SignInState()
}

sealed class SignInNavigation {
    object NavigateToMain : SignInNavigation()
}