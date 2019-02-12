package com.powerincode.questionnaire_app.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.data.preference.PreferenceProvider
import com.powerincode.questionnaire_app.screens.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * Created by powerman23rus on 12/02/2019.
 */
class MainFragment : BaseFragment() {
    @Inject lateinit var pref : PreferenceProvider

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runBlocking {
            delay(1000)
            Toast.makeText(context, pref.token, Toast.LENGTH_SHORT).show()
        }
    }
}