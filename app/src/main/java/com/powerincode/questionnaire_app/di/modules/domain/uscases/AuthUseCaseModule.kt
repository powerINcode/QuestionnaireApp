package com.powerincode.questionnaire_app.di.modules.domain.uscases

import com.powerincode.questionnaire_app.domain.uscases.UseCase
import com.powerincode.questionnaire_app.domain.uscases.auth.SignInUseCase
import com.powerincode.questionnaire_app.domain.uscases.auth.SignUpUseCase
import dagger.Binds
import dagger.Module

/**
 * Created by powerman23rus on 05/03/2019.
 */
@Module
abstract class AuthUseCaseModule {
    @Binds
    abstract fun bindSignInUsecase(useCase : SignInUseCase) : UseCase

    @Binds
    abstract fun bindSignUpUsecase(useCase : SignUpUseCase) : UseCase
}