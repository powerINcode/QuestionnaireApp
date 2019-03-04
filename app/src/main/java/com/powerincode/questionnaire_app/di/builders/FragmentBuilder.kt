package com.powerincode.questionnaire_app.di.builders

import com.powerincode.questionnaire_app.di.modules.auth.AuthModule
import com.powerincode.questionnaire_app.di.modules.MainModule
import com.powerincode.questionnaire_app.screens.auth.login.LoginFragment
import com.powerincode.questionnaire_app.screens.auth.signin.SignInFragment
import com.powerincode.questionnaire_app.screens.auth.signup.SignUpFragment
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

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun buildMainFragment() : MainFragment
}