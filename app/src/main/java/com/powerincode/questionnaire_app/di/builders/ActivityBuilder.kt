package com.powerincode.questionnaire_app.di.builders

import com.powerincode.questionnaire_app.di.modules.MainModule
import com.powerincode.questionnaire_app.screens.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by powerman23rus on 12/02/2019.
 */

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun buildMainActivity() : MainActivity
}