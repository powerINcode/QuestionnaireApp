package com.powerincode.questionnaire_app.screens.auth

import android.content.Intent
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.screens._base.activity.BaseNavigationActivity

/**
 * Created by powerman23rus on 25/02/2019.
 */
class AuthActivity : BaseNavigationActivity() {
    override fun getLayoutId() : Int = R.layout.activity_auth

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.loginFragment) {
            val intent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }
}