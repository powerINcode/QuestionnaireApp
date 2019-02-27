package com.powerincode.questionnaire_app.screens.auth.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.StringRes
import com.google.android.gms.auth.api.credentials.CredentialsClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.livedata.LiveEvent
import com.powerincode.questionnaire_app.core.livedata.MutableLiveEvent
import com.powerincode.questionnaire_app.data.local.User
import com.powerincode.questionnaire_app.screens._base.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * Created by powerman23rus on 25/02/2019.
 */
class LoginViewModel @Inject constructor(
    private val googleSignInClient : GoogleSignInClient,
    private val credentialsClient : CredentialsClient
) : BaseViewModel() {
    private val _navigation : MutableLiveEvent<LoginNavigation> = MutableLiveEvent()
    val navigation : LiveEvent<LoginNavigation> = _navigation

    private val _state : MutableLiveData<LoginViewState> = MutableLiveData()
    val state : LiveData<LoginViewState> = _state

    var firstName : String = ""
        set(value) {
            field = value
            onSignedValueChange()
        }
    var lastName : String = ""
        set(value) {
            field = value
            onSignedValueChange()
        }
    var avatar : Int = 0
        set(value) {
            field = value
            onSignedValueChange()
        }

    init {
        if (GoogleSignIn.getLastSignedInAccount(googleSignInClient.applicationContext) != null) {
            _navigation.event = LoginNavigation.NavigateToMain
        } else {
            _state.value = LoginViewState.ShowLoginState(credentialsClient)
        }
    }

    fun signUp() {
        val error = validateInputData()
        if (error == null) {
            val user = User(firstName, lastName, avatar)
            credentialsClient.save(user.toCredentional()).addOnCompleteListener {
                if (it.isSuccessful) {

                } else {
                    _message.event = "Save failed"
                }
            }
        } else {
            LoginViewState.SignUpValidationError(error)
        }
    }

    fun onSigInComplete() {
        _navigation.event = LoginNavigation.NavigateToMain
    }

    private fun onSignedValueChange() {
        val error = validateInputData()
        if (error == null) {
            _state.value = LoginViewState.SignUpReady
        } else {
            _state.value = LoginViewState.SignUpNotReady
        }
    }

    @StringRes
    private fun validateInputData() : Int? {
        return when {
            firstName.isEmpty() -> R.string.error_first_name_empty
            lastName.isEmpty() -> R.string.error_last_name_empty
            avatar == 0 -> R.string.error_avatar_empty
            else -> null
        }
    }
}