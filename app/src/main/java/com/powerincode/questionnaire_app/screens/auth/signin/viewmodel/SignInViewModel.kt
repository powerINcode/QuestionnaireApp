package com.powerincode.questionnaire_app.screens.auth.signin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.credentials.*
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.common.errorIdOrNull
import com.powerincode.questionnaire_app.core.extensions.common.exhaustive
import com.powerincode.questionnaire_app.data.realtimedatabase.dao.users.models.SaveUserParams
import com.powerincode.questionnaire_app.data.realtimedatabase.models.user.UserModel
import com.powerincode.questionnaire_app.domain.uscases.auth.SignInGoogleUseCase
import com.powerincode.questionnaire_app.domain.uscases.auth.SignInUseCase
import com.powerincode.questionnaire_app.domain.uscases.auth.SignInUseCase.SignInParam
import com.powerincode.questionnaire_app.domain.uscases.profile.SaveProfileUseCase
import com.powerincode.questionnaire_app.domain.uscases.profile.SaveRemoteProfileUseCase
import com.powerincode.questionnaire_app.domain.uscases.profile.credential.GetCredentialUseCase
import com.powerincode.questionnaire_app.domain.uscases.profile.credential.ResolveCredentialSignInUseCase
import com.powerincode.questionnaire_app.domain.uscases.profile.credential.SaveCredentialUseCase
import com.powerincode.questionnaire_app.domain.uscases.profile.credential.SaveCredentialUseCase.SaveCredentialParam
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateEmailUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidatePasswordUseCase
import com.powerincode.questionnaire_app.screens._base.viewmodel.StateViewModel
import javax.inject.Inject


/**
 * Created by powerman23rus on 27/02/2019.
 */
class SignInViewModel @Inject constructor(
    private val validateEmail : ValidateEmailUseCase,
    private val validatePassword : ValidatePasswordUseCase,
    private val getCredential : GetCredentialUseCase,
    private val saveCredential : SaveCredentialUseCase,
    private val saveRemoteProfileUseCase : SaveRemoteProfileUseCase,
    private val resolveCredential : ResolveCredentialSignInUseCase,
    private val signIn : SignInUseCase,
    private val signInGoogle : SignInGoogleUseCase,
    private val saveProfile : SaveProfileUseCase,
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
            resolveCredential()
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
            when (val result = signIn(SignInParam(_email.value, _password.value))) {
                is SignInUseCase.SignInResult.EmailError -> _errorEmail.value = result.errors.errorIdOrNull()
                is SignInUseCase.SignInResult.PasswordError -> _errorPassword.value = result.errors.errorIdOrNull()
                is SignInUseCase.SignInResult.UserNotSignInError -> _messageById.event = result.errors.errorIdOrNull()
                is SignInUseCase.SignInResult.Success -> {
                    _state.value = SignInState.SignInCompleteState

                    val user = result.user
                    handleCredentialSave(
                        saveCredential(
                            SaveCredentialParam(
                                user.uid,
                                user.email!!,
                                user.displayName,
                                result.password,
                                null,
                                null
                            )
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
        _state.value = SignInState.ClearState
        request {

            signInGoogle(account)
            val param =
                SaveUserParams(account.id!!, account.displayName, account.email, account.photoUrl?.toString(), false)
            saveRemoteProfileUseCase(param)
            handleCredentialSave(
                saveCredential(
                    SaveCredentialParam(
                        param.id,
                        param.email!!,
                        param.name + "Haha",
                        null,
                        param.avatarUrl,
                        IdentityProviders.GOOGLE
                    )
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
        _state.value = SignInState.ClearState

        if (!credential.password.isNullOrEmpty()) {
            _email.value = credential.id
            _password.value = credential.password
        }

        request {
            handleResolveCredential(resolveCredential(credential))
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
                _state.value = SignInState.ClearState
                saveUserAndNavigateToMain(state.user)
            }
            else -> {
                throw IllegalStateException("Need ${SignInState.CredentialSavePromptState::class}, but have: $state")
            }
        }
    }

    private fun handleEmailError() {
        _errorEmail.value = validateEmail.block(_email.value).errorIdOrNull()
    }

    private fun handlePasswordError() {
        _errorPassword.value = validatePassword.block(_password.value).errorIdOrNull()
    }

    private suspend fun resolveCredential() {
        when (val result = getCredential()) {
            is GetCredentialUseCase.CredentialAvailability.Available -> {
                handleResolveCredential(resolveCredential(result.credential))
            }
            is GetCredentialUseCase.CredentialAvailability.NeedResolution -> {
                _state.value = SignInState.CredentialChooseProfile(result.exception)
            }
            is GetCredentialUseCase.CredentialAvailability.Unavailable -> {
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
        }.exhaustive
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

    private fun saveUserAndNavigateToMain(user : UserModel) {
        saveProfile.block(user)
        _navigation.event = SignInNavigation.NavigateToMain
    }
}