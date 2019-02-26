package com.powerincode.questionnaire_app.screens.auth.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.common.exhaustive
import com.powerincode.questionnaire_app.core.extensions.doOnLayout
import com.powerincode.questionnaire_app.screens._base.fragment.BaseFragment
import com.powerincode.questionnaire_app.screens.auth.login.adapter.AvatarRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by powerman23rus on 25/02/2019.
 */
class LoginFragment : BaseFragment<LoginViewModel>() {
    override fun getLayoutId() : Int = com.powerincode.questionnaire_app.R.layout.fragment_login
    override fun getViewModelClass() = LoginViewModel::class.java

    private val avatarRecyclerView = AvatarRecyclerViewAdapter()

    init {
        val a = 0
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rv_login_avatars.doOnLayout {
            with(rv_login_avatars) {
                layoutManager = GridLayoutManager(
                    context,
                    rv_login_avatars.width / resources.getDimensionPixelSize(R.dimen.login_avatar_size)
                )
                adapter = avatarRecyclerView
                setHasFixedSize(true)
            }
        }

//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()
//
//        val context = context ?: return
//        val mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
//        val account = GoogleSignIn.getLastSignedInAccount(context)
//
//        if (account == null) {
//            val signInIntent = mGoogleSignInClient.signInIntent
//            startActivityForResult(signInIntent, RC_SIGN_IN)
//        } else {
//            Toast.makeText(context, "Already sign in", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun handleSignInResult(completedTask : Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            Toast.makeText(context, "!!! SIGN IN !!!!", Toast.LENGTH_SHORT).show()
            viewModel.onSigInComplete()
        } catch (e : ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(context, "SIGN IN FAILED", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onObserveViewModel(vm : LoginViewModel) {
        super.onObserveViewModel(vm)

        vm.state.observeEvent {
            when (it) {
                is LoginViewState.ShowLoginState -> {
                    val signInIntent = it.client.signInIntent
                    startActivityForResult(signInIntent, RC_SIGN_IN)
                }
            }.exhaustive
        }
    }

    override fun observeNavigation(vm : LoginViewModel) {
        super.observeNavigation(vm)

        vm.navigation.observeEvent {
            when (it) {
                LoginNavigation.NavigateToMain -> {
                    findNavController().navigate(R.id.mainActivity)
                }
            }.exhaustive
        }
    }

    companion object {
        const val RC_SIGN_IN = 1000
    }

}