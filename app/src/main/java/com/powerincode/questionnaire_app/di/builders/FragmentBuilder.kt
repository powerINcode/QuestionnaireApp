package com.powerincode.questionnaire_app.di.builders

import com.powerincode.questionnaire_app.di.modules.screens.MainModule
import com.powerincode.questionnaire_app.di.modules.screens.auth.AuthModule
import com.powerincode.questionnaire_app.screens.auth.login.LoginFragment
import com.powerincode.questionnaire_app.screens.auth.login.LoginFragment_tmp
import com.powerincode.questionnaire_app.screens.auth.signin.view.SignInFragment
import com.powerincode.questionnaire_app.screens.auth.signup.view.SignUpFragment
import com.powerincode.questionnaire_app.screens.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by powerman23rus on 12/02/2019.
 */

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector(modules = [AuthModule::class])
    abstract fun buildLoginFragment() : LoginFragment

    @ContributesAndroidInjector(modules = [AuthModule::class])
    abstract fun buildSignInFragment() : SignInFragment

    @ContributesAndroidInjector(modules = [AuthModule::class])
    abstract fun buildSignUpFragment() : SignUpFragment

    @ContributesAndroidInjector(modules = [AuthModule::class])
    abstract fun buildasMainFragment() : LoginFragment_tmp

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun buildMainFragment() : MainFragment
}