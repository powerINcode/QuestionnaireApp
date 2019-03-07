package com.powerincode.questionnaire_app.di.modules.app

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.powerincode.questionnaire_app.App
import com.powerincode.questionnaire_app.di.modules.app.data.DataModule
import com.powerincode.questionnaire_app.di.modules.app.repository.RepositoryModule
import dagger.Module
import dagger.Provides
import javax.inject.Provider

/**
 * Created by powerman23rus on 12/02/2019.
 */

@Module(
    includes = [
        DataModule::class,
        RepositoryModule::class,
        GoogleAuthModule::class
    ]
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

}