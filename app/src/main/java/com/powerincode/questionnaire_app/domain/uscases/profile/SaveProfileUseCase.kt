package com.powerincode.questionnaire_app.domain.uscases.profile

import com.powerincode.questionnaire_app.data.realtimedatabase.models.user.UserModel
import com.powerincode.questionnaire_app.domain.repository.UserRepository
import com.powerincode.questionnaire_app.domain.uscases.BaseUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 05/03/2019.
 */
class SaveProfileUseCase @Inject constructor(private val userRepository : UserRepository) :
    BaseUseCase<UserModel, BaseUseCase.None>() {
    override suspend fun run(param : UserModel) : None = wrapNone {
        userRepository.saveUser(param)
    }
}
