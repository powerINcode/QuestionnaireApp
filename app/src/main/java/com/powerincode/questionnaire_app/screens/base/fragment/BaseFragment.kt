package com.powerincode.questionnaire_app.screens.base.fragment

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.powerincode.questionnaire_app.screens.base.viewmodel.BaseViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by powerman23rus on 12/02/2019.
 */
abstract class BaseFragment<T : BaseViewModel> : Fragment() {
    abstract fun getViewModelClass() : Class<T>

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    lateinit var viewModel : T

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            viewModel = ViewModelProviders.of(it, factory).get(getViewModelClass())
            onObserveViewModel(viewModel)
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    protected open fun onObserveViewModel(vm : T) {

    }
}