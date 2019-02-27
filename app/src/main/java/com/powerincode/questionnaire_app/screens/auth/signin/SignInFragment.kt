package com.powerincode.questionnaire_app.screens.auth.signin

import android.os.Bundle
import android.view.View
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.common.exhaustive
import com.powerincode.questionnaire_app.core.extensions.afterTextChanged
import com.powerincode.questionnaire_app.core.extensions.textIfDifferent
import com.powerincode.questionnaire_app.screens._base.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_sign_in.*

/**
 * Created by powerman23rus on 27/02/2019.
 */
class SignInFragment : BaseFragment<SignInViewModel>() {
    override fun getLayoutId() = R.layout.fragment_sign_in
    override fun getViewModelClass() = SignInViewModel::class.java
    override fun fragmentTag() = "SignInFragment"

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_singin_email.afterTextChanged { viewModel.setUserEmail(it) }
        et_singin_password.afterTextChanged { viewModel.setUserPassword(it) }

        btn_signin.setOnClickListener { viewModel.onSignInClick() }
        btn_signin_connect_google.setOnClickListener { viewModel.onGoogleSignInClick() }
    }

    override fun onObserveViewModel(vm : SignInViewModel) {
        super.onObserveViewModel(vm)

        vm.email.observeNullable { et_singin_email.textIfDifferent = it }
        vm.password.observeNullable { et_singin_password.textIfDifferent = it }
    }

    override fun observeNavigation(vm : SignInViewModel) {
        super.observeNavigation(vm)

        vm.state.observe {
            when(it) {
                is SignInState.Error -> handleErrors(it.errors)
                is SignInState.ClearErrors -> {
                    tin_signin_email.error = null
                    tin_signin_password.error = null
                }
            }.exhaustive
        }
    }

    private fun handleErrors(errors : List<SignInError>) {
        for (error in errors) {
            when(error){
                is SignInError.EmailError -> tin_signin_email.error = getString(error.messageId)
                is SignInError.PasswordError ->  tin_signin_password.error = getString(error.messageId)
                is SignInError.AuthError -> {}
            }.exhaustive
        }
    }
}