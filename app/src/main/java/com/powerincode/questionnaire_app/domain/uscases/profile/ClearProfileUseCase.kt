package com.powerincode.questionnaire_app.domain.uscases.profile

import com.powerincode.questionnaire_app.domain.uscases.BaseUseCase
import com.powerincode.questionnaire_app.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by powerman23rus on 05/03/2019.
 */
class ClearProfileUseCase @Inject constructor(private val userRepository : UserRepository) :
    BaseUseCase<BaseUseCase.None, BaseUseCase.None>() {

    override suspend fun run(param : None) : None = wrapNone {
        userRepository.deleteUser()
    }
}