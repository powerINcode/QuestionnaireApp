package com.powerincode.questionnaire_app.domain.uscases.profile

import com.powerincode.questionnaire_app.domain.repository.UserRepository
import com.powerincode.questionnaire_app.domain.uscases.BaseUseCase
import com.powerincode.questionnaire_app.domain.uscases.NoArgsUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 05/03/2019.
 */
class ClearProfileUseCase @Inject constructor(private val userRepository : UserRepository) :
    NoArgsUseCase<BaseUseCase.None>() {

    override suspend fun run(param : None) : None = wrapNone {
        userRepository.deleteUser()
    }
}