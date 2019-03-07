package com.powerincode.questionnaire_app.domain.uscases.profile

import com.powerincode.questionnaire_app.data.local.User
import com.powerincode.questionnaire_app.data.preference.PreferenceProvider
import com.powerincode.questionnaire_app.domain.uscases.BaseUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 05/03/2019.
 */
class SaveProfileUseCase @Inject constructor(private val preferenceProvider : PreferenceProvider) :
    BaseUseCase<User, BaseUseCase.None>() {
    override suspend fun run(param : User) : None = wrapNone {
        preferenceProvider.user = param
    }
}
