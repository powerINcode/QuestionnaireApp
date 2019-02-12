package com.powerincode.questionnaire_app.di.keys

import com.powerincode.questionnaire_app.screens.base.BaseViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by powerman23rus on 12/02/2019.
 */

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value : KClass<out BaseViewModel>)