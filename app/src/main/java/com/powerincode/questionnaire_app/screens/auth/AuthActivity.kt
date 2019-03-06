package com.powerincode.questionnaire_app.screens.auth

import android.content.Intent
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.screens._base.activity.BaseNavigationActivity
import com.powerincode.questionnaire_app.screens.auth.login.LoginFragment

/**
 * Created by powerman23rus on 25/02/2019.
 */
class AuthActivity : BaseNavigationActivity() {
    override fun getLayoutId() : Int = R.layout.activity_auth
    override fun getInitialFragment() = LoginFragment.getFragment()

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        when (requestCode) {
            RC_CREDENTIAL_HINT,
            RC_CREDENTIAL_SIGN_IN_RESOLVE,
            RC_CREDENTIAL_SAVE_RESOLVE
            -> lastPushedFragment?.onActivityResult(
                requestCode,
                resultCode,
                data
            )
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        const val RC_GOOGLE_SIGN_IN = 1000
        const val RC_CREDENTIAL_HINT = 1001
        const val RC_CREDENTIAL_SIGN_IN_RESOLVE = 1002
        const val RC_CREDENTIAL_SAVE_RESOLVE = 1003
    }
}