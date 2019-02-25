package com.powerincode.questionnaire_app.di.modules.app

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.powerincode.questionnaire_app.App
import com.powerincode.questionnaire_app.data.preference.PreferenceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by powerman23rus on 12/02/2019.
 */

@Module(
    includes = [
        NetworkModule::class]
)
object AppModule {

    @JvmStatic
    @Provides
    fun provideAppContext(application : App) : Context = application

    @Suppress("UNCHECKED_CAST")
    @Provides
    @JvmStatic
    fun provideViewModelFactory(viewModels : Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass : Class<T>) : T {
                val viewModelProvider =
                    viewModels[modelClass] ?: throw IllegalArgumentException("ViewModel class $modelClass not found")
                return viewModelProvider.get() as T
            }
        }

    @Singleton
    @JvmStatic
    @Provides
    fun providePreference(context : Context) : PreferenceProvider = PreferenceProvider(context)

}