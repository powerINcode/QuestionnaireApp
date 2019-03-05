package com.powerincode.questionnaire_app.screens.auth.signin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.common.errorIdOrNull
import com.powerincode.questionnaire_app.core.extensions.common.exhaustive
import com.powerincode.questionnaire_app.domain.uscases.auth.SignInUseCase
import com.powerincode.questionnaire_app.domain.uscases.profile.SaveProfileUseCase
import com.powerincode.questionnaire_app.screens._base.viewmodel.StateViewModel
import com.powerincode.questionnaire_app.screens.auth.signin.interactor.SignInInteractor
import javax.inject.Inject


/**
 * Created by powerman23rus on 27/02/2019.
 */
class SignInViewModel @Inject constructor(
    private val signInInteractor : SignInInteractor,
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

    fun onSignInClick() {
        handleEmailError()
        handlePasswordError()

        request {
            when (val result = signInInteractor.signIn(_email.value, _password.value)) {
                is SignInUseCase.SignInResult.EmailError -> _errorEmail.value = result.errors.errorIdOrNull()
                is SignInUseCase.SignInResult.PasswordError -> _errorPassword.value = result.errors.errorIdOrNull()
                is SignInUseCase.SignInResult.UserNotSignInError -> _messageById.event = result.errors.errorIdOrNull()
                is SignInUseCase.SignInResult.Success -> {
                    handleSaveUserState(signInInteractor.saveProfile(result.user))
                }
            }.exhaustive
        }
    }

    fun onGoogleSignInClick() {
        _state.value =
            SignInState.GoogleSignInState(googleSignInClient)
    }

    fun onGoogleSignInSuccess(account : GoogleSignInAccount) {
        handleSaveUserState(signInInteractor.saveProfile(account))
    }

    fun onGoogleSignInFailed() {
        _messageById.event = R.string.error_signin_firebase_error
    }

    private fun handleEmailError() {
        _errorEmail.value = signInInteractor.validateEmail(_email.value).errorIdOrNull()
    }

    private fun handlePasswordError() {
        _errorPassword.value = signInInteractor.validatePassword(_password.value).errorIdOrNull()
    }

    private fun handleSaveUserState(saveState : SaveProfileUseCase.SaveProfileResult) {
        when (saveState) {
            is SaveProfileUseCase.SaveProfileResult.Success -> navigateToMain()
            else -> _messageById.event = saveState.errors.errorIdOrNull()
        }.exhaustive
    }

    private fun navigateToMain() {
        _navigation.event = SignInNavigation.NavigateToMain
    }
}