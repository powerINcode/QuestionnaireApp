package com.powerincode.questionnaire_app.di.components

import com.powerincode.questionnaire_app.App
import com.powerincode.questionnaire_app.di.builders.ActivityBuilder
import com.powerincode.questionnaire_app.di.builders.FragmentBuilder
import com.powerincode.questionnaire_app.di.modules.app.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by powerman23rus on 12/02/2019.
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuilder::class,
        FragmentBuilder::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app : App) : Builder

        fun build() : AppComponent
    }

    fun inject(app : App)
}