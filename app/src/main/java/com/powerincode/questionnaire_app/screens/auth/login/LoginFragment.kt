package com.powerincode.questionnaire_app.screens.auth.login

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.credentials.CredentialRequest
import com.google.android.gms.auth.api.credentials.CredentialsClient
import com.google.android.gms.common.api.ResolvableApiException
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.common.exhaustive
import com.powerincode.questionnaire_app.core.extensions.views.doOnLayout
import com.powerincode.questionnaire_app.core.extensions.views.toast
import com.powerincode.questionnaire_app.core.views.recyclerview.BaseRecyclerView
import com.powerincode.questionnaire_app.data.local.avatars.Avatar
import com.powerincode.questionnaire_app.screens._base.fragment.BaseFragment
import com.powerincode.questionnaire_app.screens.auth.login.adapter.AvatarRecyclerViewAdapter
import com.powerincode.questionnaire_app.screens.main.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by powerman23rus on 25/02/2019.
 */
class LoginFragment : BaseFragment<LoginViewModel>() {
    override fun fragmentTag() : String = "LoginFragment"
    override fun getLayoutId() : Int = com.powerincode.questionnaire_app.R.layout.fragment_login
    override fun getViewModelClass() = LoginViewModel::class.java

    private val avatarAdapter = AvatarRecyclerViewAdapter()

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rv_login_avatars.doOnLayout {
            with(rv_login_avatars) {
                itemAnimator = null
                layoutManager = GridLayoutManager(
                    context,
                    rv_login_avatars.width / resources.getDimensionPixelSize(R.dimen.login_avatar_size)
                )
                adapter = avatarAdapter.apply { swapData(Avatar.values().map { it.drawableId }) }
                setHasFixedSize(true)
            }
        }

        avatarAdapter.setRecycleListener(object : BaseRecyclerView.OnItemClick<Int> {
            override fun onClick(sender : BaseRecyclerView<*, *>, position : Int, item : Int) {
                viewModel.avatar = item
            }

        })

        fb_login_sign_up.setOnClickListener {
            viewModel.signUp()
        }

        watchInputFields()
    }

    override fun onObserveViewModel(vm : LoginViewModel) {
        super.onObserveViewModel(vm)

        vm.state.observe {
            when (it) {
                is LoginViewState.ShowLoginState -> {}
                LoginViewState.SignUpReady -> onSingUpReady()
                LoginViewState.SignUpNotReady -> onSingUpNotReady()
                is LoginViewState.SignUpValidationError -> toast(it.messageId)
            }.exhaustive
        }
    }

    override fun observeNavigation(vm : LoginViewModel) {
        super.observeNavigation(vm)

        vm.navigation.observeEvent {
            when (it) {
                LoginNavigation.NavigateToMain -> {
                    notifyStartActivityAndClear(Intent(context, MainActivity::class.java))
                }
            }.exhaustive
        }
    }

    @SuppressLint("RestrictedApi")
    private fun onSingUpReady() {
        fb_login_sign_up.visibility = View.VISIBLE
    }

    @SuppressLint("RestrictedApi")
    private fun onSingUpNotReady() {
        fb_login_sign_up.visibility = View.GONE
    }

    private fun onShowLogin(client : CredentialsClient) {
        client
            .request(
                CredentialRequest.Builder()
                    .setPasswordLoginSupported(true)
                    .build()
            )
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val player = 1

                } else {
                    val exception = it.exception
                    if (exception is ResolvableApiException) {
                        try {
                            exception.startResolutionForResult(activity, REQUEST_SAVE)
                        } catch (e: IntentSender.SendIntentException) {
                            Log.e(ContentValues.TAG, "STATUS: Failed to send resolution.", e)
                        }
                    }
                }
            }
    }

    private fun watchInputFields() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s : CharSequence?, start : Int, count : Int, after : Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            // showing the floating action button if avatar is selected and input data is valid
            override fun afterTextChanged(s: Editable) {
                if (et_login_first_name.text.toString() == s.toString()) {
                    viewModel.firstName = s.toString()
                } else {
                    viewModel.lastName = s.toString()
                }
            }
        }

        et_login_first_name.addTextChangedListener(textWatcher)
        et_login_last_name.addTextChangedListener(textWatcher)
    }

    companion object {
        const val REQUEST_SAVE = 1000
    }
}