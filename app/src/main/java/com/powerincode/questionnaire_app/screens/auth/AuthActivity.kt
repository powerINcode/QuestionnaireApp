package com.powerincode.questionnaire_app.screens.auth

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.screens._base.activity.BaseNavigationActivity
import com.powerincode.questionnaire_app.screens.auth.login.LoginFragment

/**
 * Created by powerman23rus on 25/02/2019.
 */
class AuthActivity : BaseNavigationActivity() {
    override fun getLayoutId() : Int = R.layout.activity_auth
    override fun getInitialFragment() = LoginFragment.getFragment()
}