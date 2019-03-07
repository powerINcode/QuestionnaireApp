package com.powerincode.questionnaire_app.domain.uscases.auth

import com.google.android.gms.auth.api.credentials.Credential
import com.powerincode.questionnaire_app.domain.repository.CredentialRepository
import com.powerincode.questionnaire_app.domain.uscases.BaseUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 06/03/2019.
 */
class GetCredentialUseCase @Inject constructor(private val credentialRepository : CredentialRepository) :
    BaseUseCase<BaseUseCase.None, Credential>() {

    override suspend fun run(param : None) : Credential {
        return credentialRepository.getCredential()
    }
}