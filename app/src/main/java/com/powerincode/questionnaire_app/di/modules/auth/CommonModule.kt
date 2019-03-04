package com.powerincode.questionnaire_app.di.modules.auth

import com.powerincode.questionnaire_app.domain.auth.common.usecase.ValidateEmailUseCase
import com.powerincode.questionnaire_app.domain.auth.common.usecase.ValidatePasswordUseCase
import dagger.Module
import dagger.Provides

/**
 * Created by powerman23rus on 04/03/2019.
 */
@Module
object CommonModule {
    @JvmStatic
    @Provides
    fun provideValidateEmailUseCase() = ValidateEmailUseCase()

    @JvmStatic
    @Provides
    fun provideValidatePasswordUseCase() = ValidatePasswordUseCase()
}