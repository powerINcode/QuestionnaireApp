package com.powerincode.questionnaire_app.di.modules.domain

import com.powerincode.questionnaire_app.di.modules.domain.uscases.AuthUseCaseModule
import com.powerincode.questionnaire_app.di.modules.domain.uscases.ProfileUseCaseModule
import com.powerincode.questionnaire_app.di.modules.domain.uscases.ValidationCaseModule
import dagger.Module

/**
 * Created by powerman23rus on 05/03/2019.
 */
@Module(
    includes = [
        ValidationCaseModule::class,
        AuthUseCaseModule::class,
        ProfileUseCaseModule::class
    ]
)
class UseCasesModule