package com.powerincode.questionnaire_app.screens.auth.signup.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.common.errorIdOrNull
import com.powerincode.questionnaire_app.core.extensions.common.exhaustive
import com.powerincode.questionnaire_app.domain.uscases.auth.SignUpUseCase
import com.powerincode.questionnaire_app.screens._base.viewmodel.StateViewModel
import com.powerincode.questionnaire_app.screens.auth.signup.interactor.SignUpInteractor
import javax.inject.Inject

/**
 * Created by powerman23rus on 27/02/2019.
 */

class SignUpViewModel @Inject constructor(
    private val signUpInteractor : SignUpInteractor
) : StateViewModel<SignUpState, SignUpNavigation>() {

    private val _name : MutableLiveData<String?> = MutableLiveData()
    val name : LiveData<String?> = _name

    private val _email : MutableLiveData<String?> = MutableLiveData()
    val email : LiveData<String?> = _email

    private val _password : MutableLiveData<String?> = MutableLiveData()
    val password : LiveData<String?> = _password

    private val _confirmPassword : MutableLiveData<String?> = MutableLiveData()
    val confirmPassword : LiveData<String?> = _confirmPassword

    //region Errors
    private val _errorName : MutableLiveData<Int?> = MutableLiveData()
    val errorName : LiveData<Int?> = _errorName

    private val _errorEmail : MutableLiveData<Int?> = MutableLiveData()
    val errorEmail : LiveData<Int?> = _errorEmail

    private val _errorPassword : MutableLiveData<Int?> = MutableLiveData()
    val errorPassword : LiveData<Int?> = _errorPassword

    private val _errorConfirmPassword : MutableLiveData<Int?> = MutableLiveData()
    val errorConfirmPassword : LiveData<Int?> = _errorConfirmPassword
    //endregion

    fun onNameChange(name : String?) {
        _name.value = name
        handleNameError()
    }

    fun onEmailChange(email : String?) {
        _email.value = email
        handleEmailError()
    }

    fun onPasswordChange(password : String?) {
        _password.value = password
        handlePasswordError()
    }

    fun onConfirmPasswordChange(confirmPassword : String?) {
        _confirmPassword.value = confirmPassword
        handleConfirmPasswordError()
    }


    fun onSignUpClick() {
        handleNameError()
        handleEmailError()
        handlePasswordError()
        handleConfirmPasswordError()

        request {
            when (val result =
                signUpInteractor.register(_name.value, _email.value, _password.value, _confirmPassword.value)) {
                is SignUpUseCase.SignUpResult.NameError -> _errorName.value = result.errors.errorIdOrNull()
                is SignUpUseCase.SignUpResult.EmailError -> _errorEmail.value = result.errors.errorIdOrNull()
                is SignUpUseCase.SignUpResult.PasswordError -> _errorName.value = result.errors.errorIdOrNull()
                is SignUpUseCase.SignUpResult.PasswordEqualityError -> {
                    _errorPassword.value = result.errors.errorIdOrNull()
                    _errorConfirmPassword.value = result.errors.errorIdOrNull()
                }
                SignUpUseCase.SignUpResult.UserNotCreatedError -> _messageById.event = R.string.error_signin_firebase_error
                SignUpUseCase.SignUpResult.Success -> _navigation.event = SignUpNavigation.NavigateToSignIn
            }.exhaustive
        }
    }


    //region Validation
    private fun handleNameError() {
        _errorName.value = signUpInteractor.validateName(_name.value).firstOrNull()?.messageId
    }

    private fun handleEmailError() {
        _errorEmail.value = signUpInteractor.validateEmail(_email.value).firstOrNull()?.messageId
    }

    private fun handlePasswordError() {
        val validatePassword = signUpInteractor.validatePassword(_password.value).firstOrNull()?.messageId
        val validatePasswordEquality =
            signUpInteractor.validatePasswordsEquality(_password.value, _confirmPassword.value).firstOrNull()
                ?.messageId

        when {
            validatePassword != null -> _errorPassword.value = validatePassword
            else -> handlePasswordsEquality(validatePasswordEquality)
        }
    }

    private fun handleConfirmPasswordError() {
        val validateConfirmPassword = signUpInteractor.validatePassword(_confirmPassword.value).firstOrNull()?.messageId
        val validatePasswordEquality =
            signUpInteractor.validatePasswordsEquality(_password.value, _confirmPassword.value).firstOrNull()
                ?.messageId

        when {
            validateConfirmPassword != null -> _errorConfirmPassword.value = validateConfirmPassword
            else -> handlePasswordsEquality(validatePasswordEquality)
        }
    }
    private fun handlePasswordsEquality(error : Int?) {
        _errorPassword.value = error
        _errorConfirmPassword.value = error
    }
    //endregion
}