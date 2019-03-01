package com.powerincode.questionnaire_app.screens.auth

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.screens._base.activity.BaseNavigationActivity
import com.powerincode.questionnaire_app.screens.auth.signup.SignUpFragment

/**
 * Created by powerman23rus on 25/02/2019.
 */
class AuthActivity : BaseNavigationActivity() {
    override fun getLayoutId() : Int = R.layout.activity_auth
    override fun getInitialFragment() = SignUpFragment.getFragment()

//    override fun onBackPressed() {
//        if (lastPushedFragment is LoginFragment) {
//            val intent = Intent(Intent.ACTION_MAIN).apply {
//                addCategory(Intent.CATEGORY_HOME)
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            }
//            startActivity(intent)
//        } else {
//            super.onBackPressed()
//        }
//    }
}