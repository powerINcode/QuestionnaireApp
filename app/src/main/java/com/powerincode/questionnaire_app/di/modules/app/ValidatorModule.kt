package com.powerincode.questionnaire_app.di.modules.app

import com.powerincode.questionnaire_app.core.validators.EmailValidator
import com.powerincode.questionnaire_app.core.validators.NameValidator
import com.powerincode.questionnaire_app.core.validators.PasswordValidator
import dagger.Module
import dagger.Provides

/**
 * Created by powerman23rus on 28/02/2019.
 */

@Module
object ValidatorModule {
    @JvmStatic
    @Provides
    fun provideNameValidator() = NameValidator()

    @JvmStatic
    @Provides
    fun provideEmailValidator() = EmailValidator()

    @JvmStatic
    @Provides
    fun providePasswordValidator() = PasswordValidator()
}