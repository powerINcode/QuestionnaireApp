package com.powerincode.questionnaire_app.screens.auth.signin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.credentials.*
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.ResolvableApiException
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.common.errorIdOrNull
import com.powerincode.questionnaire_app.core.extensions.common.exhaustive
import com.powerincode.questionnaire_app.data.local.User
import com.powerincode.questionnaire_app.domain.uscases.auth.ResolveCredentialSignInUseCase
import com.powerincode.questionnaire_app.domain.uscases.auth.SaveCredentialUseCase
import com.powerincode.questionnaire_app.domain.uscases.auth.SignInUseCase
import com.powerincode.questionnaire_app.screens._base.viewmodel.StateViewModel
import com.powerincode.questionnaire_app.screens.auth.signin.interactor.SignInInteractor
import javax.inject.Inject


/**
 * Created by powerman23rus on 27/02/2019.
 */
class SignInViewModel @Inject constructor(
    private val signInInteractor : SignInInteractor,
    private val credentialsClient : CredentialsClient,
    private val googleSignInClient : GoogleSignInClient
) : StateViewModel<SignInState, SignInNavigation>() {
    private var _email : MutableLiveData<String?> = MutableLiveData()
    var email : LiveData<String?> = _email

    private var _password : MutableLiveData<String?> = MutableLiveData()
    var password : LiveData<String?> = _password

    //region Errors
    private val _errorEmail : MutableLiveData<Int?> = MutableLiveData()
    val errorEmail : LiveData<Int?> = _errorEmail

    private val _errorPassword : MutableLiveData<Int?> = MutableLiveData()
    val errorPassword : LiveData<Int?> = _errorPassword

    //endregion

    init {
        _state.value = SignInState.ClearState
        request {
            try {
                val credential = signInInteractor.getCredential()
                handleResolveCredential(signInInteractor.resolveSignInCredential(credential))
            } catch (e : ResolvableApiException) {
                resolveCredentialResult(e)
            }
        }
    }

    fun setUserEmail(email : String?) {
        if (_email.value != email) {
            _email.value = email
            handleEmailError()
        }
    }

    fun setUserPassword(password : String?) {
        if (_password.value != password) {
            _password.value = password
            handlePasswordError()
        }
    }

    fun onCredentialHintSuccess(credential : Credential) {
        _email.value = credential.id
    }

    fun onSignInClick() {
        handleEmailError()
        handlePasswordError()

        request {
            when (val result = signInInteractor.signIn(_email.value, _password.value)) {
                is SignInUseCase.SignInResult.EmailError -> _errorEmail.value = result.errors.errorIdOrNull()
                is SignInUseCase.SignInResult.PasswordError -> _errorPassword.value = result.errors.errorIdOrNull()
                is SignInUseCase.SignInResult.UserNotSignInError -> _messageById.event = result.errors.errorIdOrNull()
                is SignInUseCase.SignInResult.Success -> {
                    _state.value = SignInState.SignInCompleteState

                    val user = result.user
                    handleCredentialSave(
                        signInInteractor.saveCredential(
                            user.uid,
                            user.email!!,
                            user.displayName,
                            result.password,
                            null
                        )
                    )
                }
            }.exhaustive
        }
    }

    /**
     * On user click on sign in button
     */
    fun onGoogleSignInClick() {
        _state.value = SignInState.GoogleSignInState(googleSignInClient)
    }

    /**
     * On user click on Google sign in button
     */
    fun onGoogleSignInSuccess(account : GoogleSignInAccount) {
        request {
            handleCredentialSave(
                signInInteractor.saveCredential(
                    account.id!!,
                    account.email!!,
                    account.displayName,
                    null,
                    IdentityProviders.GOOGLE
                )
            )
        }
    }

    /**
     * On sign in via Google account failed
     */
    fun onGoogleSignInFailed() {
        _state.value = SignInState.ClearState
        _messageById.event = com.powerincode.questionnaire_app.R.string.error_signin_firebase_error
    }

    /**
     * When need to resolve account for credential
     */
    fun onCredentialChooseProfileSuccess(credential : Credential) {
        request {
            handleResolveCredential(signInInteractor.resolveSignInCredential(credential))
        }
    }

    /**
     * Credential-related request failed
     */
    fun onCredentialFailed() {
        _state.value = SignInState.ClearState
    }

    /**
     * User accepted to save his credential
     */
    fun onCredentialSavePromptComplete() {
        when (val state = _state.value) {
            is SignInState.CredentialSavePromptState -> {
                saveUserAndNavigateToMain(state.user)
            }
            else -> {
                throw IllegalStateException("Need CredentialSavePromptState, but have: $state")
            }
        }
    }

    private fun handleEmailError() {
        _errorEmail.value = signInInteractor.validateEmail(_email.value).errorIdOrNull()
    }

    private fun handlePasswordError() {
        _errorPassword.value = signInInteractor.validatePassword(_password.value).errorIdOrNull()
    }

    private fun resolveCredentialResult(rae : ResolvableApiException) {
        if (rae.statusCode != CommonStatusCodes.SIGN_IN_REQUIRED) {
            _state.value = SignInState.CredentialChooseProfile(rae)
        } else {
            val hintRequest = HintRequest.Builder()
                .setHintPickerConfig(
                    CredentialPickerConfig.Builder()
                        .setShowCancelButton(true)
                        .build()
                )
                .setEmailAddressIdentifierSupported(true)
                .setAccountTypes(IdentityProviders.GOOGLE)
                .build()

            _state.value = SignInState.CredentialHints(credentialsClient, hintRequest)
        }
    }

    private fun handleCredentialSave(saveCredentialResult : SaveCredentialUseCase.SaveCredentialsResult) {
        request {
            when (saveCredentialResult) {
                is SaveCredentialUseCase.SaveCredentialsResult.PasswordError -> {
                    _errorPassword.value = saveCredentialResult.errors.errorIdOrNull()
                }
                is SaveCredentialUseCase.SaveCredentialsResult.CredentialSavePromptError -> {
                    _state.value = SignInState.CredentialSavePromptState(
                        saveCredentialResult.user,
                        saveCredentialResult.exception
                    )
                }
                is SaveCredentialUseCase.SaveCredentialsResult.Success -> {
                    saveUserAndNavigateToMain(saveCredentialResult.user)
                }
                is SaveCredentialUseCase.SaveCredentialsResult.Canceled -> {
                    saveUserAndNavigateToMain(saveCredentialResult.user)
                }
            }.exhaustive
        }
    }

    private fun handleResolveCredential(resolveCredentialResult : ResolveCredentialSignInUseCase.ResolveCredentialResult) {
        when (resolveCredentialResult) {
            is ResolveCredentialSignInUseCase.ResolveCredentialResult.ManualSignIn -> {
                _email.value = resolveCredentialResult.email
                _password.value = resolveCredentialResult.password
                onSignInClick()
            }
            is ResolveCredentialSignInUseCase.ResolveCredentialResult.GoogleSignIn -> onGoogleSignInSuccess(
                resolveCredentialResult.user
            )
            is ResolveCredentialSignInUseCase.ResolveCredentialResult.UnknownAccountType -> _messageById.event =
                R.string.error_signin_firebase_credential_error
        }.exhaustive
    }

    private fun saveUserAndNavigateToMain(user : User) {
        signInInteractor.saveProfile(user)
        _navigation.event = SignInNavigation.NavigateToMain
    }
}