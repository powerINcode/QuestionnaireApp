package com.powerincode.questionnaire_app.screens

import android.os.Bundle
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.screens.base.activity.BaseNavigationActivity

class MainActivity : BaseNavigationActivity() {
    override fun getLayoutId() : Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
