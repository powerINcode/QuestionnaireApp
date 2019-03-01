package com.powerincode.questionnaire_app.screens.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.common.exhaustive
import com.powerincode.questionnaire_app.screens._base.fragment.BaseFragment
import com.powerincode.questionnaire_app.screens.auth.AuthActivity
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by powerman23rus on 12/02/2019.
 */
class MainFragment : BaseFragment<MainViewModel>() {
    override fun fragmentTag() : String = "MainFragment"
    override fun getViewModelClass() = MainViewModel::class.java
    override fun getLayoutId() : Int = R.layout.fragment_main

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(fragmentTag())
        test.animate()
            .translationX(500f)
            .setDuration(4000)
            .start()

        btn_log_out.setOnClickListener {
            viewModel.signOut()
        }
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

    override fun observeNavigation(vm : MainViewModel) {
        super.observeNavigation(vm)

        vm.navigation.observeEvent {
            when(it) {
                MainNavigation.NavigateToAuth -> {
//                    findNavController().navigate(R.id.authActivity)
//                    notifyFinishActivity()

                    notifyStartActivityAndClear(Intent(context, AuthActivity::class.java))
                }
            }.exhaustive
        }
    }
}