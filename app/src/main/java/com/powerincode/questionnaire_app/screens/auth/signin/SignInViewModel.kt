package com.powerincode.questionnaire_app.screens.auth.signin

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.StringRes
import android.util.Patterns
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.firebase.await
import com.powerincode.questionnaire_app.screens._base.viewmodel.StateViewModel
import javax.inject.Inject


/**
 * Created by powerman23rus on 27/02/2019.
 */
class SignInViewModel @Inject constructor(
    private val firebaseAuth : FirebaseAuth,
    private val googleSignInClient : GoogleSignInClient
) : StateViewModel<SignInState, SignInNavigation>() {
    private var _email : MutableLiveData<String?> = MutableLiveData()
    var email : LiveData<String?> = _email

    private var _password : MutableLiveData<String?> = MutableLiveData()
    var password : LiveData<String?> = _password

    private val errors : MutableList<SignInError> = mutableListOf()

    init {
        val currentUser = firebaseAuth.currentUser
//        googleSignInClient.las
    }

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

        clearErrors()

        handleEmailValidation(email)?.let {
            addError(SignInError.EmailError(it))
        }

        handlePasswordValidation(password)?.let {
            addError(SignInError.PasswordError(it))
        }

        if (errors.isEmpty()) {
            clearErrors()

            request {
                try {
                    firebaseAuth.signInWithEmailAndPassword(email!!, password!!).await()
                    _state.value = SignInState.SignInCompleteState
                } catch (e : FirebaseAuthInvalidUserException) {
                    addError(SignInError.AuthError(R.string.error_signin_invalid_user))
                } catch (e : FirebaseAuthInvalidCredentialsException) {
                    addError(SignInError.AuthError(R.string.error_signin_invalid_password))
                }
            }
        }
    }

    fun onGoogleSignInClick() {
        _state.value = SignInState.GoogleSignInState(googleSignInClient)
    }

    fun onSignUpClick() {

    }

    fun onGoogleSignInSuccess(account : GoogleSignInAccount) {
        val email = account.email
        googleSignInClient.signOut()
        _state.value = SignInState.SignInCompleteState
    }

    fun onGoogleSignInFailed() {

    }

    private fun addError(error : SignInError) {
        errors.firstOrNull { it.javaClass == error.javaClass }?.let {
            errors.remove(it)
        }

        errors.add(error)
        _state.value = SignInState.ErrorState(errors)
    }

    private fun clearErrors() {
        errors.clear()
        _state.value = SignInState.ClearErrorsState
    }

    @StringRes
    private fun handleEmailValidation(email : String?) : Int? {
        return when {
            email.isNullOrEmpty() -> com.powerincode.questionnaire_app.R.string.error_signin_email_empty
            !Patterns.EMAIL_ADDRESS.toRegex().containsMatchIn(email) -> com.powerincode.questionnaire_app.R.string.error_signin_email_incorrect
            else -> null
        }
    }

    @StringRes
    private fun handlePasswordValidation(password : String?) : Int? {
        return when {
            password.isNullOrEmpty() -> com.powerincode.questionnaire_app.R.string.error_signin_password_empty
            password.length < 6 -> com.powerincode.questionnaire_app.R.string.error_signin_password_incorrect
            else -> null
        }
    }
}