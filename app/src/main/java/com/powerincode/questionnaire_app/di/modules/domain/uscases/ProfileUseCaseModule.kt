package com.powerincode.questionnaire_app.di.modules.domain.uscases

import com.powerincode.questionnaire_app.domain.uscases.UseCase
import com.powerincode.questionnaire_app.domain.uscases.profile.ClearProfileUseCase
import com.powerincode.questionnaire_app.domain.uscases.profile.GetProfileUseCase
import com.powerincode.questionnaire_app.domain.uscases.profile.SaveProfileUseCase
import dagger.Binds
import dagger.Module

/**
 * Created by powerman23rus on 05/03/2019.
 */
@Module
abstract class ProfileUseCaseModule {
    @Binds
    abstract fun bindGetProfileUseCase(useCase : GetProfileUseCase) : UseCase

    @Binds
    abstract fun bindSaveProfileUseCase(useCase : SaveProfileUseCase) : UseCase

    @Binds
    abstract fun bindClearProfileUseCase(useCase : ClearProfileUseCase) : UseCase
}