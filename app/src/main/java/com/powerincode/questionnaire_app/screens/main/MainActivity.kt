package com.powerincode.questionnaire_app.screens.main

import android.content.Context
import android.content.Intent
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.screens._base.activity.BaseNavigationActivity

class MainActivity : BaseNavigationActivity() {
    override fun getLayoutId() : Int = R.layout.activity_main
    override fun getInitialFragment() = MainFragment()

    companion object {
        fun getIntent(context : Context?) = Intent(context, MainActivity::class.java)
    }
}
