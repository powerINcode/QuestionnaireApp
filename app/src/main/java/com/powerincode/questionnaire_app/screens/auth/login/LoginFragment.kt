package com.powerincode.questionnaire_app.screens.auth.login

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.screens.base.fragment.BaseFragment

/**
 * Created by powerman23rus on 25/02/2019.
 */
class LoginFragment : BaseFragment<LoginViewModel>() {
    override fun getLayoutId() : Int = R.layout.fragment_login
    override fun getViewModelClass() = LoginViewModel::class.java



}