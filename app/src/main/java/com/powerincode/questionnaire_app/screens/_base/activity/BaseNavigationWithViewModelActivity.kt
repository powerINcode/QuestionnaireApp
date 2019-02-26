package com.powerincode.questionnaire_app.screens._base.activity

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.powerincode.questionnaire_app.screens._base.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * Created by powerman23rus on 12/02/2019.
 */
abstract class BaseNavigationWithViewModelActivity<T : BaseViewModel> : BaseNavigationActivity() {
    abstract fun getViewModelClass() : Class<T>

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    lateinit var viewModel : T

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(getViewModelClass())
    }

}