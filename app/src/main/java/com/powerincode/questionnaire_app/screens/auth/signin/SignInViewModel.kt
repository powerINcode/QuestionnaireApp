package com.powerincode.questionnaire_app.screens.auth.signin

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.StringRes
import android.util.Patterns
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.screens._base.viewmodel.StateViewModel
import javax.inject.Inject

/**
 * Created by powerman23rus on 27/02/2019.
 */
class SignInViewModel @Inject constructor() : StateViewModel<SignInState, SignInNavigation>() {
    private var _email : MutableLiveData<String?> = MutableLiveData()
    var email : LiveData<String?> = _email

    private var _password : MutableLiveData<String?> = MutableLiveData()
    var password : LiveData<String?> = _password

    private val errors : MutableList<SignInError> = mutableListOf()

    fun setUserEmail(email : String?) {
        if (_email.value != email) {
            _email.value = email
        }
    }

    fun setUserPassword(password : String?) {
        if (_password.value != password) {
            _password.value = password
        }
    }

    fun onSignInClick() {
        val email = _email.value
        val password = _password.value

        clearErros()

        val emailError = handleEmailValidation(email)?.let {
            addError(SignInError.EmailError(it))
        }
        val passwordError = handlePasswordValidation(password)?.let {
            addError(SignInError.PasswordError(it))
        }

        if (emailError == null && passwordError == null) {
            _state.value = SignInState.ClearErrors
            _message.event = "Start sign in"
        }
    }

    fun onGoogleSignInClick() {

    }

    fun onSignUpClick() {

    }

    private fun addError(error : SignInError) {
        errors.firstOrNull { it.javaClass == error.javaClass }?.let {
            errors.remove(it)
        }

        errors.add(error)
        _state.value = SignInState.Error(errors)
    }

    private fun clearErros() {
        errors.clear()
        _state.value = SignInState.ClearErrors
    }

    @StringRes
    private fun handleEmailValidation(email : String?) : Int? {
        return when {
            email.isNullOrEmpty() -> R.string.error_signin_email_empty
            !Patterns.EMAIL_ADDRESS.toRegex().containsMatchIn(email) -> R.string.error_signin_email_incorrect
            else -> null
        }
    }

    @StringRes
    private fun handlePasswordValidation(password : String?) : Int? {
        return when {
            password.isNullOrEmpty() -> R.string.error_signin_password_empty
            password.length < 6 -> R.string.error_signin_password_incorrect
            else -> null
        }
    }
}