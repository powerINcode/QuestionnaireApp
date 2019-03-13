package com.powerincode.questionnaire_app.screens.auth.signup.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.credentials.*
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.common.errorIdOrNull
import com.powerincode.questionnaire_app.core.extensions.common.exhaustive
import com.powerincode.questionnaire_app.core.validators.rules.EqualityRule
import com.powerincode.questionnaire_app.domain.uscases.auth.SignUpUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateEmailUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateNameUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidatePasswordUseCase
import com.powerincode.questionnaire_app.screens._base.viewmodel.StateViewModel
import javax.inject.Inject

/**
 * Created by powerman23rus on 27/02/2019.
 */

class SignUpViewModel @Inject constructor(
    private val validateEmail : ValidateEmailUseCase,
    private val validateName : ValidateNameUseCase,
    private val validatePassword : ValidatePasswordUseCase,
    private val signUp : SignUpUseCase,
    private val credentialsClient : CredentialsClient
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

    init {
        val hintRequest = HintRequest.Builder()
            .setHintPickerConfig(
                CredentialPickerConfig.Builder()
                    .setShowCancelButton(true)
                    .build()
            )
            .setEmailAddressIdentifierSupported(true)
            .setAccountTypes(IdentityProviders.GOOGLE)
            .build()

        _state.value = SignUpState.CredentialHints(credentialsClient, hintRequest)
    }

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

    fun onCredentialHintSuccess(credential : Credential) {
        _state.value = SignUpState.ClearState
        _name.value = credential.name
        _email.value = credential.id
    }

    fun onCredentialHintFailed() {
        _state.value = SignUpState.ClearState
    }


    fun onSignUpClick() {
        handleNameError()
        handleEmailError()
        handlePasswordError()
        handleConfirmPasswordError()

        request {
            val param = SignUpUseCase.SignUpParam(_name.value, _email.value, _password.value, _confirmPassword.value)
            when (val result = signUp(param)) {
                is SignUpUseCase.SignUpResult.NameError -> _errorName.value = result.errors.errorIdOrNull()
                is SignUpUseCase.SignUpResult.EmailError -> _errorEmail.value = result.errors.errorIdOrNull()
                is SignUpUseCase.SignUpResult.PasswordError -> _errorName.value = result.errors.errorIdOrNull()
                is SignUpUseCase.SignUpResult.PasswordEqualityError -> {
                    _errorPassword.value = result.errors.errorIdOrNull()
                    _errorConfirmPassword.value = result.errors.errorIdOrNull()
                }
                SignUpUseCase.SignUpResult.UserNotCreatedError -> _messageById.event =
                    R.string.error_signin_firebase_error
                SignUpUseCase.SignUpResult.Success -> _navigation.event = SignUpNavigation.NavigateToSignIn
            }.exhaustive
        }
    }


    //region Validation
    private fun handleNameError() {
        _errorName.value = validateName.block(_name.value).firstOrNull()?.messageId
    }

    private fun handleEmailError() {
        _errorEmail.value = validateEmail.block(_email.value).firstOrNull()?.messageId
    }

    private fun handlePasswordError() {
        val validatePassword = validatePassword.block(_password.value).firstOrNull()?.messageId
        val validatePasswordEquality =
            EqualityRule(_password.value, _confirmPassword.value, R.string.error_signup_password_not_equals).validate()
                ?.messageId

        when {
            validatePassword != null -> _errorPassword.value = validatePassword
            else -> handlePasswordsEquality(validatePasswordEquality)
        }
    }

    private fun handleConfirmPasswordError() {
        val validateConfirmPassword =
            validatePassword.block(_confirmPassword.value).firstOrNull()?.messageId
        val validatePasswordEquality =
            EqualityRule(_password.value, _confirmPassword.value, R.string.error_signup_password_not_equals).validate()
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