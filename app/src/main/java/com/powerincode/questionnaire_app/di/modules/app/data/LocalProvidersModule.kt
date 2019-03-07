package com.powerincode.questionnaire_app.di.modules.app.data

import android.content.Context
import com.powerincode.questionnaire_app.data.preference.PreferenceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by powerman23rus on 21/02/2019.
 */

@Module
object LocalProvidersModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideLocalPreferences(context : Context) = PreferenceProvider(context)
}