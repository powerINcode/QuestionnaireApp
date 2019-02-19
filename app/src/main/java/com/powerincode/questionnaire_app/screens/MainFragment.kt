package com.powerincode.questionnaire_app.screens

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.screens.base.fragment.BaseFragment

/**
 * Created by powerman23rus on 12/02/2019.
 */
class MainFragment : BaseFragment<MainViewModel>() {
    override fun getViewModelClass() = MainViewModel::class.java

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onCreated()
    }

    override fun onObserveViewModel(vm : MainViewModel) {
        super.onObserveViewModel(vm)

        var i = 1
        viewModel.getMessage().observe(this, Observer {
            Toast.makeText(context, "$it: $i", Toast.LENGTH_SHORT).show()
            i++
        })
    }
}