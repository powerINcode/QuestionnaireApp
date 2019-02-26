package com.powerincode.questionnaire_app.di.builders

import com.powerincode.questionnaire_app.di.modules.LoginModule
import com.powerincode.questionnaire_app.di.modules.MainModule
import com.powerincode.questionnaire_app.screens.main.MainFragment
import com.powerincode.questionnaire_app.screens.auth.login.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by powerman23rus on 12/02/2019.
 */

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun buildLoginFragment() : LoginFragment

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun buildMainFragment() : MainFragment
}