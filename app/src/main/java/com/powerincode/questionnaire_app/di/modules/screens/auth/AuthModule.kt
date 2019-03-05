package com.powerincode.questionnaire_app.di.modules.screens.auth

import dagger.Module

/**
 * Created by powerman23rus on 25/02/2019.
 */

@Module(includes = [
    LoginModule::class,
    SignInModule::class,
    SignUpModule::class
])
abstract class AuthModule