package com.powerincode.questionnaire_app.screens.base

import android.content.Context
import android.support.v4.app.Fragment
import dagger.android.support.AndroidSupportInjection

/**
 * Created by powerman23rus on 12/02/2019.
 */
abstract class BaseFragment : Fragment() {
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}