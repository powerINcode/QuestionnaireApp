package com.powerincode.questionnaire_app.screens.auth.login

import android.os.Bundle
import android.view.View
import com.powerincode.questionnaire_app.core.extensions.common.exhaustive
import com.powerincode.questionnaire_app.screens._base.fragment.BaseFragment
import com.powerincode.questionnaire_app.screens.auth.signin.view.SignInFragment
import com.powerincode.questionnaire_app.screens.auth.signup.view.SignUpFragment
import com.powerincode.questionnaire_app.screens.main.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by powerman23rus on 25/02/2019.
 */
class LoginFragment : BaseFragment<LoginViewModel>() {
    override fun fragmentTag() : String = "LoginFragment"
    override fun getLayoutId() : Int = com.powerincode.questionnaire_app.R.layout.fragment_login
    override fun getViewModelClass() = LoginViewModel::class.java


    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_login_signin.setOnClickListener { viewModel.onSignInClick() }
        btn_login_signup.setOnClickListener { viewModel.onSignUpClick() }
    }

    override fun observeNavigation(vm : LoginViewModel) {
        super.observeNavigation(vm)

        vm.navigation.observeEvent {
            when (it) {
                LoginNavigation.NavigateToSignIn -> {
                    pushFragment(SignInFragment.getFragment())
                }

                LoginNavigation.NavigateToSignUp -> {
                    pushFragment(SignUpFragment.getFragment())
                }
                LoginNavigation.NavigateToMain -> {
                    notifyStartActivity(MainActivity.getIntent(context))
                    notifyFinishActivity()
                }
            }.exhaustive
        }
    }

    companion object {
        @JvmStatic
        fun getFragment() = LoginFragment()
    }
}