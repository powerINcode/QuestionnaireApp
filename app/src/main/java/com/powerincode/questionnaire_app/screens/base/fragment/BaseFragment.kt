package com.powerincode.questionnaire_app.screens.base.fragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.powerincode.questionnaire_app.core.extensions.observeEvent
import com.powerincode.questionnaire_app.core.livedata.LiveEvent
import com.powerincode.questionnaire_app.core.livedata.observers.NotNullObserver
import com.powerincode.questionnaire_app.screens.base.viewmodel.BaseViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by powerman23rus on 12/02/2019.
 */
abstract class BaseFragment<T : BaseViewModel> : Fragment() {
    @LayoutRes
    abstract fun getLayoutId() : Int
    abstract fun getViewModelClass() : Class<T>

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    lateinit var viewModel : T

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            viewModel = ViewModelProviders.of(it, factory).get(getViewModelClass())
            observeNavigation(viewModel)
            onObserveViewModel(viewModel)
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    protected open fun observeNavigation(vm : T) {}

    protected open fun onObserveViewModel(vm : T) {

    }

    protected fun <T2> LiveData<T2>.observe(block : (T2) -> Unit) {
        observe(this@BaseFragment, NotNullObserver {
            block(it)
        })
    }

    protected fun <T2> LiveData<T2>.observeNullable(block : (T2?) -> Unit) {
        observe(this@BaseFragment, Observer {
            block(it)
        })
    }

    protected fun <T2> LiveEvent<T2>.observeEvent(block : (T2) -> Unit) {
        observeEvent(this@BaseFragment, block)
    }
}