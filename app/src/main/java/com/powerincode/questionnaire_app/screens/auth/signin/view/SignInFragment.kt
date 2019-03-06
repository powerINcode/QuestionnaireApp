package com.powerincode.questionnaire_app.screens.auth.signin.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.powerincode.questionnaire_app.core.extensions.common.exhaustive
import com.powerincode.questionnaire_app.core.extensions.views.afterTextChanged
import com.powerincode.questionnaire_app.core.extensions.views.getStringOrNull
import com.powerincode.questionnaire_app.core.extensions.views.textIfDifferent
import com.powerincode.questionnaire_app.screens._base.fragment.BaseFragment
import com.powerincode.questionnaire_app.screens.auth.AuthActivity.Companion.RC_CREDENTIAL_HINT
import com.powerincode.questionnaire_app.screens.auth.AuthActivity.Companion.RC_CREDENTIAL_SAVE_RESOLVE
import com.powerincode.questionnaire_app.screens.auth.AuthActivity.Companion.RC_CREDENTIAL_SIGN_IN_RESOLVE
import com.powerincode.questionnaire_app.screens.auth.AuthActivity.Companion.RC_GOOGLE_SIGN_IN
import com.powerincode.questionnaire_app.screens.auth.signin.viewmodel.SignInNavigation
import com.powerincode.questionnaire_app.screens.auth.signin.viewmodel.SignInState
import com.powerincode.questionnaire_app.screens.auth.signin.viewmodel.SignInViewModel
import com.powerincode.questionnaire_app.screens.main.MainActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*

/**
 * Created by powerman23rus on 27/02/2019.
 */
class SignInFragment : BaseFragment<SignInViewModel>() {
    override fun getLayoutId() = com.powerincode.questionnaire_app.R.layout.fragment_sign_in
    override fun getViewModelClass() = SignInViewModel::class.java
    override fun fragmentTag() = "SignInFragment"

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_singin_email.afterTextChanged { viewModel.setUserEmail(it) }
        et_singin_password.afterTextChanged { viewModel.setUserPassword(it) }

        btn_login_signin.setOnClickListener { viewModel.onSignInClick() }
        btn_signin_connect_google.setOnClickListener { viewModel.onGoogleSignInClick() }
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_GOOGLE_SIGN_IN -> handleGoogleSignInIntent(resultCode, data)
            RC_CREDENTIAL_HINT -> handleCredentialHintIntent(resultCode, data)
            RC_CREDENTIAL_SIGN_IN_RESOLVE -> handleCredentialShowProfileIntent(resultCode, data)
            RC_CREDENTIAL_SAVE_RESOLVE -> handleCredentialPromptIntent()
        }
    }

    override fun onObserveViewModel(vm : SignInViewModel) {
        super.onObserveViewModel(vm)

        vm.email.observeNullable { et_singin_email.textIfDifferent = it }
        vm.errorEmail.observeNullable { tin_signin_email.error = getStringOrNull(it) }
        vm.errorPassword.observeNullable { tin_signin_password.error = getStringOrNull(it) }
        vm.password.observeNullable { et_singin_password.textIfDifferent = it }


        vm.state.observe {
            when(it) {
                is SignInState.ClearState -> {}
                is SignInState.GoogleSignInState -> showGoogleSignIn(it)
                is SignInState.CredentialHints -> showCredentialHints(it)
                is SignInState.CredentialChooseProfile -> showCredentialChooseProfile(it)
                is SignInState.CredentialSavePromptState -> showCredentialSavePrompt(it)
                SignInState.SignInCompleteState -> { }
            }.exhaustive
        }
    }

    override fun observeNavigation(vm : SignInViewModel) {
        super.observeNavigation(vm)

         vm.navigation.observeEvent {
             when(it) {
                 SignInNavigation.NavigateToMain -> notifyStartActivityAndClear(MainActivity.getIntent(context))
             }.exhaustive
         }

    }


    private fun showGoogleSignIn(googleSignInState : SignInState.GoogleSignInState) {
        startActivityForResult(googleSignInState.client.signInIntent,
            RC_GOOGLE_SIGN_IN
        )
    }

    private fun handleGoogleSignInIntent(resultCode : Int, data : Intent?) {
        if (resultCode == RESULT_OK) {
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = accountTask.getResult(ApiException::class.java)
            if (account != null) {
                viewModel.onGoogleSignInSuccess(account)
            } else {
                viewModel.onGoogleSignInFailed()
            }
        } else {
            viewModel.onGoogleSignInFailed()
        }
    }

    private fun showCredentialHints(state : SignInState.CredentialHints) {
        try {
            val intent = state.credentialClient.getHintPickerIntent(state.hintRequest)
            activity?.startIntentSenderForResult(intent.intentSender, RC_CREDENTIAL_HINT, null, 0, 0, 0)
        } catch (e : IntentSender.SendIntentException) {
            viewModel.onCredentialFailed()
        }
    }

    private fun handleCredentialHintIntent(resultCode : Int, data : Intent?) {
        if (resultCode == RESULT_OK && data != null) {
            val credential = data.getParcelableExtra<Credential>(Credential.EXTRA_KEY)
            viewModel.onCredentialHintSuccess(credential)
        }
    }

    private fun showCredentialChooseProfile(state : SignInState.CredentialChooseProfile) {
        try {
            state.resolveException.startResolutionForResult(activity, RC_CREDENTIAL_SIGN_IN_RESOLVE)
        } catch (e : IntentSender.SendIntentException) {
            viewModel.onCredentialFailed()
        }
    }

    private fun handleCredentialShowProfileIntent(resultCode : Int, data : Intent?) {
        if (resultCode == RESULT_OK && data != null) {
            val credential = data.getParcelableExtra<Credential>(Credential.EXTRA_KEY)
            viewModel.onCredentialChooseProfileSuccess(credential)
        } else {
            viewModel.onCredentialFailed()
        }
    }

    private fun showCredentialSavePrompt(state : SignInState.CredentialSavePromptState) {
        try {
            state.resolveException.startResolutionForResult(activity, RC_CREDENTIAL_SAVE_RESOLVE)
        } catch (e : IntentSender.SendIntentException) {
            viewModel.onCredentialFailed()
        }
    }

    private fun handleCredentialPromptIntent() {
        viewModel.onCredentialSavePromptComplete()
    }

    companion object {
        @JvmStatic
        fun getFragment() = SignInFragment()
    }
}