package com.powerincode.questionnaire_app.di.modules

import com.powerincode.questionnaire_app.data.preference.PreferenceProvider
import com.powerincode.questionnaire_app.screens.MainViewModel
import dagger.Module

/**
 * Created by powerman23rus on 12/02/2019.
 */
@Module
abstract class MainModule {
    abstract fun bindMainModule(preference : PreferenceProvider) : MainViewModel
}