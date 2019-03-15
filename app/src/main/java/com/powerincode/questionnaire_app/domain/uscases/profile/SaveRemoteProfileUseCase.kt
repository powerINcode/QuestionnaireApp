package com.powerincode.questionnaire_app.domain.uscases.profile

import com.powerincode.questionnaire_app.data.realtimedatabase.dao.users.models.SaveUserParams
import com.powerincode.questionnaire_app.domain.repository.RemoteUserRepository
import com.powerincode.questionnaire_app.domain.uscases.BaseUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 05/03/2019.
 */
class SaveRemoteProfileUseCase @Inject constructor(private val remoteUserRepository : RemoteUserRepository) :
    BaseUseCase<SaveUserParams, BaseUseCase.None>() {
    override suspend fun run(param : SaveUserParams) : None = wrapNone {
        remoteUserRepository.saveUser(param)
    }
}
