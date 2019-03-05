package com.powerincode.questionnaire_app.di.modules.domain.uscases

import com.powerincode.questionnaire_app.domain.uscases.UseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateEmailUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidatePasswordUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateNameUseCase
import dagger.Binds
import dagger.Module

/**
 * Created by powerman23rus on 05/03/2019.
 */
@Module
abstract class ValidationCaseModule {
    @Binds
    abstract fun provideValidateEmailUseCase(useCase : ValidateEmailUseCase) : UseCase

    @Binds
    abstract fun provideValidatePasswordUseCase(useCase : ValidatePasswordUseCase) : UseCase

    @Binds
    abstract fun provideValidateNameUseCase(useCase : ValidateNameUseCase) : UseCase
}