package com.powerincode.questionnaire_app.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.screens.base.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by powerman23rus on 12/02/2019.
 */
class MainFragment : BaseFragment<MainViewModel>() {
    override fun getViewModelClass() = MainViewModel::class.java
    override fun getLayoutId() : Int = R.layout.fragment_main

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        test.animate()
            .translationX(500f)
            .setDuration(4000)
            .start()
    }

    override fun onObserveViewModel(vm : MainViewModel) {
        super.onObserveViewModel(vm)

        viewModel.message.observeEvent {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loader.observeEvent {
            pb_loading.visibility = if(it) View.VISIBLE else View.INVISIBLE
        }

    }
}