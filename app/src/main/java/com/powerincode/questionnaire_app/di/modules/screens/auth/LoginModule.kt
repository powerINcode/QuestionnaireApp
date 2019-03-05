package com.powerincode.questionnaire_app.di.modules.screens.auth

import android.arch.lifecycle.ViewModel
import com.powerincode.questionnaire_app.di.keys.ViewModelKey
import com.powerincode.questionnaire_app.screens.auth.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by powerman23rus on 04/03/2019.
 */
@Module
abstract class LoginModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(model : LoginViewModel) : ViewModel
}