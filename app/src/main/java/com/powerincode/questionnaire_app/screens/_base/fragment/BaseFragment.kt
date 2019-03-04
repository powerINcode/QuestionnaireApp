package com.powerincode.questionnaire_app.screens._base.fragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.powerincode.questionnaire_app.core.extensions.common.toDp
import com.powerincode.questionnaire_app.core.extensions.livedata.observeEvent
import com.powerincode.questionnaire_app.core.extensions.views.isVisible
import com.powerincode.questionnaire_app.core.extensions.views.toast
import com.powerincode.questionnaire_app.core.livedata.LiveEvent
import com.powerincode.questionnaire_app.core.livedata.observers.NotNullObserver
import com.powerincode.questionnaire_app.screens._base.activity.BaseActivity
import com.powerincode.questionnaire_app.screens._base.activity.BaseNavigationActivity
import com.powerincode.questionnaire_app.screens._base.viewmodel.BaseViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by powerman23rus on 12/02/2019.
 */
abstract class BaseFragment<T : BaseViewModel> : Fragment(), CoroutineScope {
    private val job = Job()
    override val coroutineContext : CoroutineContext = Dispatchers.Main + job

    @LayoutRes
    abstract fun getLayoutId() : Int

    abstract fun getViewModelClass() : Class<T>
    abstract fun fragmentTag() : String

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    lateinit var viewModel : T

    protected val root : BaseActivity?
        get() = activity as? BaseActivity

    private var loadingCount : Int = 0
    private var loadingView : View? = null

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        loadingView = ProgressBar(context).apply {
            layoutParams = FrameLayout.LayoutParams(36.toDp().toInt(), 36.toDp().toInt()).apply {
                gravity = Gravity.CENTER
            }

            isVisible = false
        }

        container?.addView(loadingView)
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onDestroyView() {
        (view?.parent as? ViewGroup)?.removeView(loadingView)
        loadingView = null
        loadingCount = 0

        cancel()
        super.onDestroyView()
    }



    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        activity?.let {
            viewModel = ViewModelProviders.of(it, factory).get(getViewModelClass())
            observeNavigation(viewModel)
            onObserveViewModel(viewModel)
        }
    }

    override fun onAttach(context : Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    protected open fun observeNavigation(vm : T) {}

    protected open fun onObserveViewModel(vm : T) {
        vm.messageId.observeEvent {
            toast(it)
        }

        vm.message.observeEvent {
            toast(it)
        }

        vm.loader.observeEvent {
            if (it) {
                showLoader()
            } else {
                hideLoader()
            }
        }
    }

    protected fun setTitle(@StringRes resId : Int) = setTitle(getString(resId))
    protected fun setTitle(title : String) : Unit? {
        return root?.supportActionBar?.setTitle(title)
    }

    protected fun showLoader() {
        loadingCount++
        loadingView?.bringToFront()
        loadingView?.isVisible = true
    }

    protected fun hideLoader() {
        loadingCount--
        if (loadingCount <= 0) {
            loadingCount = 0
            loadingView?.isVisible = false
        }
    }

    protected fun notifyStartActivity(intent : Intent) = activity?.startActivity(intent)
    protected fun notifyStartActivityForResult(intent : Intent, requestCode : Int) =
        activity?.startActivityForResult(intent, requestCode)

    protected fun notifyStartActivityAndClear(intent : Intent) {
        notifyFinishAffinityActivity()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        notifyStartActivity(intent)
    }

    protected fun notifyFinishActivity() = activity?.finish()
    protected fun notifyFinishAffinityActivity() = activity?.finishAffinity()

    protected fun pushFragment(fragment : BaseFragment<*>) {
        (activity as? BaseNavigationActivity)?.pushFragment(fragment)
    }

    protected fun addFragment(fragment : BaseFragment<*>) {
        (activity as? BaseNavigationActivity)?.addFragment(fragment)
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