package com.powerincode.questionnaire_app.screens.auth.login

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.doOnLayout
import com.powerincode.questionnaire_app.screens.auth.login.adapter.AvatarRecyclerViewAdapter
import com.powerincode.questionnaire_app.screens.base.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by powerman23rus on 25/02/2019.
 */
class LoginFragment : BaseFragment<LoginViewModel>() {
    override fun getLayoutId() : Int = R.layout.fragment_login
    override fun getViewModelClass() = LoginViewModel::class.java

    private val avatarRecyclerView = AvatarRecyclerViewAdapter()

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
    }


}