package com.powerincode.questionnaire_app.screens._base.activity

import android.os.Bundle
import android.support.annotation.AnimRes
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.screens._base.fragment.BaseFragment

/**
 * Created by powerman23rus on 12/02/2019.
 */
abstract class BaseNavigationActivity : BaseActivity() {
    abstract fun getInitialFragment() : BaseFragment<*>

    protected var lastPushedFragment : BaseFragment<*>? = null

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        setFragment(getInitialFragment(), false)
    }

    override fun onDestroy() {
        lastPushedFragment = null
        super.onDestroy()
    }

    fun pushFragment(fragment : BaseFragment<*>) {
        setFragment(
            fragment,
            true,
            fragment.fragmentTag(),
            R.anim.enter_anim,
            R.anim.exit_anim,
            R.anim.pop_enter_anim,
            R.anim.pop_exit_anim
        )
    }

    fun addFragment(fragment : BaseFragment<*>) {
        setFragment(fragment, true, null)
    }

    protected fun setFragment(
        fragment : BaseFragment<*>,
        isAddToBackStack : Boolean,
        tag : String? = null,
        @AnimRes enterAnim : Int? = null,
        @AnimRes exitAnim : Int? = null,
        @AnimRes popEnterAnim : Int? = null,
        @AnimRes popExitAnim : Int? = null
    ) {
        val resultFragment = supportFragmentManager.findFragmentByTag(tag) ?: fragment
        val transaction = supportFragmentManager.beginTransaction()

        if (enterAnim != null && exitAnim != null && popEnterAnim != null && popExitAnim != null) {
            transaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }


        transaction.replace(R.id.nav_host_fragment, resultFragment, tag)

        if (isAddToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
        lastPushedFragment = resultFragment as BaseFragment<*>
    }
}