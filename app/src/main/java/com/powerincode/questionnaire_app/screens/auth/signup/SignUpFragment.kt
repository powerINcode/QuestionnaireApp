package com.powerincode.questionnaire_app.screens.auth.signup

import android.os.Bundle
import android.view.View
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.views.afterTextChanged
import com.powerincode.questionnaire_app.core.extensions.views.fadeIn
import com.powerincode.questionnaire_app.core.extensions.views.getStringOrNull
import com.powerincode.questionnaire_app.core.extensions.views.textIfDifferent
import com.powerincode.questionnaire_app.screens._base.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by powerman23rus on 28/02/2019.
 */
class SignUpFragment : BaseFragment<SignUpViewModel>() {
    override fun getLayoutId() = R.layout.fragment_sign_up
    override fun getViewModelClass() = SignUpViewModel::class.java
    override fun fragmentTag() = "SignUpFragment"

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animateTitle()

        et_signup_name.afterTextChanged { viewModel.onNameChange(it) }
        et_signup_email.afterTextChanged { viewModel.onEmailChange(it) }
        et_signup_password.afterTextChanged { viewModel.onPasswordChange(it) }
        et_signup_confirm_password.afterTextChanged { viewModel.onConfirmPasswordChange(it) }
        btn_signup.setOnClickListener { viewModel.onSignUpClick() }
    }

    private fun animateTitle() {
        launch {
            delay(250)
            tv_signup_create.fadeIn()
            delay(150)
            tv_signup_account.fadeIn()
        }
    }

    override fun onObserveViewModel(vm : SignUpViewModel) {
        super.onObserveViewModel(vm)

        with(vm) {
            name.observeNullable { et_signup_name.textIfDifferent = it }
            email.observeNullable { et_signup_email.textIfDifferent = it }
            password.observeNullable { et_signup_password.textIfDifferent = it }
            confirmPassword.observeNullable { et_signup_confirm_password.textIfDifferent = it }

            errorName.observeNullable(::handleNameError)
            errorEmail.observeNullable(::handleEmailError)
            errorPassword.observeNullable(::handlePasswordError)
            errorConfirmPassword.observeNullable(::handleConfirmPasswordError)
        }

    }

    override fun observeNavigation(vm : SignUpViewModel) {
        super.observeNavigation(vm)

    }

    private fun handleNameError(messageId : Int?) {
        tin_sugnup_name.error = getStringOrNull(messageId)
    }

    private fun handleEmailError(messageId : Int?) {
        tin_sugnup_email.error = getStringOrNull(messageId)
    }

    private fun handlePasswordError(messageId : Int?) {
        tin_sugnup_password.error = getStringOrNull(messageId)
    }

    private fun handleConfirmPasswordError(messageId : Int?) {
        tin_sugnup_confirm_password.error = getStringOrNull(messageId)
    }

    companion object {
        @JvmStatic
        fun getFragment() = SignUpFragment()
    }
}