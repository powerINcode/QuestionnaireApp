package com.powerincode.questionnaire_app.di.modules.domain.uscases

import com.powerincode.questionnaire_app.domain.uscases.UseCase
import com.powerincode.questionnaire_app.domain.uscases.auth.*
import dagger.Binds
import dagger.Module

/**
 * Created by powerman23rus on 05/03/2019.
 */
@Module
abstract class AuthUseCaseModule {
    @Binds
    abstract fun bindSignInUseCase(useCase : SignInUseCase) : UseCase

    @Binds
    abstract fun bindSignUpUseCase(useCase : SignUpUseCase) : UseCase

    @Binds
    abstract fun bindGetCredentialUseCase(useCase : GetCredentialUseCase) : UseCase

    @Binds
    abstract fun bindSaveCredentialUseCase(useCase : SaveCredentialUseCase) : UseCase

    @Binds
    abstract fun bindResolveCredentialSignInUseCase(useCase : ResolveCredentialSignInUseCase) : UseCase
}