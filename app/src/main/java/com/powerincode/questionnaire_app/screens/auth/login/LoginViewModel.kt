package com.powerincode.questionnaire_app.screens.auth.login

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.powerincode.questionnaire_app.core.livedata.LiveEvent
import com.powerincode.questionnaire_app.core.livedata.MutableLiveEvent
import com.powerincode.questionnaire_app.screens._base.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * Created by powerman23rus on 25/02/2019.
 */
class LoginViewModel @Inject constructor(private val googleSignInClient : GoogleSignInClient) : BaseViewModel() {
    private val _navigation : MutableLiveEvent<LoginNavigation> = MutableLiveEvent()
    val navigation : LiveEvent<LoginNavigation> = _navigation

    private val _state : MutableLiveEvent<LoginViewState> = MutableLiveEvent()
    val state : LiveEvent<LoginViewState> = _state

    init {
        if (GoogleSignIn.getLastSignedInAccount(googleSignInClient.applicationContext) != null) {
            _navigation.event = LoginNavigation.NavigateToMain
        } else {
            _state.event = LoginViewState.ShowLoginState(googleSignInClient)
        }
    }

    fun onSigInComplete() {
        _navigation.event = LoginNavigation.NavigateToMain
    }
}