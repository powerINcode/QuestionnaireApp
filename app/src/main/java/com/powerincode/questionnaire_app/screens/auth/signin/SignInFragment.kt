package com.powerincode.questionnaire_app.screens.auth.signin

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.powerincode.questionnaire_app.core.extensions.common.exhaustive
import com.powerincode.questionnaire_app.core.extensions.views.afterTextChanged
import com.powerincode.questionnaire_app.core.extensions.views.getStringOrNull
import com.powerincode.questionnaire_app.core.extensions.views.textIfDifferent
import com.powerincode.questionnaire_app.core.extensions.views.toast
import com.powerincode.questionnaire_app.screens._base.fragment.BaseFragment
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

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                handleGoogleSignInIntent(data)
            } else {
                viewModel.onGoogleSignInFailed()
            }
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
                is SignInState.GoogleSignInState -> handleGoogleSignIn(it)
                SignInState.SignInCompleteState -> {
                    toast("Sign in in COMPLETE")
                }
            }.exhaustive
        }
    }

    override fun observeNavigation(vm : SignInViewModel) {
        super.observeNavigation(vm)


    }


    private fun handleGoogleSignIn(googleSignInState : SignInState.GoogleSignInState) {
        startActivityForResult(googleSignInState.client.signInIntent, RC_GOOGLE_SIGN_IN)
    }

    private fun handleGoogleSignInIntent(data : Intent?) {
        val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = accountTask.getResult(ApiException::class.java)
        if (account != null) {
            viewModel.onGoogleSignInSuccess(account)
        } else {
            viewModel.onGoogleSignInFailed()
        }
    }

    companion object {
        const val RC_GOOGLE_SIGN_IN = 1000

        @JvmStatic
        fun getFragment() = SignInFragment()
    }
}