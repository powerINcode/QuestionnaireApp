package com.powerincode.questionnaire_app.screens.auth.signin

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.screens._base.fragment.BaseFragment

/**
 * Created by powerman23rus on 27/02/2019.
 */
class SignInFragment : BaseFragment<SignInViewModel>() {
    override fun getLayoutId() = R.layout.fragment_sign_in
    override fun getViewModelClass() = SignInViewModel::class.java
    override fun fragmentTag() = "SignInFragment"
}