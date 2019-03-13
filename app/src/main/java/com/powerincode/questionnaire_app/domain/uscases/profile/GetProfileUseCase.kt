package com.powerincode.questionnaire_app.domain.uscases.profile

import com.powerincode.questionnaire_app.data.local.User
import com.powerincode.questionnaire_app.domain.repository.UserRepository
import com.powerincode.questionnaire_app.domain.uscases.NoArgsUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 05/03/2019.
 */
class GetProfileUseCase @Inject constructor(private val userRepository : UserRepository) :
    NoArgsUseCase<User?>() {

    override suspend fun run(param : None) : User? {
        return userRepository.getUser()
    }
}