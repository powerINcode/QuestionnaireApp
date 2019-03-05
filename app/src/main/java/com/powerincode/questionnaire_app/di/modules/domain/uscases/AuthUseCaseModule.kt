package com.powerincode.questionnaire_app.di.modules.domain.uscases

import com.powerincode.questionnaire_app.domain.uscases.UseCase
import com.powerincode.questionnaire_app.domain.uscases.auth.SignInUseCase
import dagger.Binds
import dagger.Module

/**
 * Created by powerman23rus on 05/03/2019.
 */
@Module
abstract class AuthUseCaseModule {
    @Binds
    abstract fun bindSignInUsecase(useCase : SignInUseCase) : UseCase
}