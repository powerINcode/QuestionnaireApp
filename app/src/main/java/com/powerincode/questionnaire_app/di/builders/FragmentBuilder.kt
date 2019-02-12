package com.powerincode.questionnaire_app.di.builders

import com.powerincode.questionnaire_app.screens.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by powerman23rus on 12/02/2019.
 */

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector
    abstract fun buildMainFragment() : MainFragment
}