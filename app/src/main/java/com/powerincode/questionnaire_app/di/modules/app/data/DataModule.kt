package com.powerincode.questionnaire_app.di.modules.app.data

import dagger.Module

/**
 * Created by powerman23rus on 07/03/2019.
 */
@Module(includes = [
    LocalProvidersModule::class,
    NetworkModule::class
])
class DataModule