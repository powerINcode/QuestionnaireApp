package com.powerincode.questionnaire_app.domain.uscases.profile

import com.powerincode.questionnaire_app.data.local.User
import com.powerincode.questionnaire_app.data.preference.PreferenceProvider
import com.powerincode.questionnaire_app.domain.uscases.UseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 05/03/2019.
 */
class GetProfileUseCase @Inject constructor(private val preferenceProvider : PreferenceProvider) :
    UseCase {
    fun execute() : User? = preferenceProvider.user
}