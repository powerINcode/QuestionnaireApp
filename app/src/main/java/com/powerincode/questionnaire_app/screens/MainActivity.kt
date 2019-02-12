package com.powerincode.questionnaire_app.screens

import android.os.Bundle
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.screens.base.BaseNavigationActivity
import javax.inject.Inject

class MainActivity : BaseNavigationActivity() {
    @Inject
    lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}
