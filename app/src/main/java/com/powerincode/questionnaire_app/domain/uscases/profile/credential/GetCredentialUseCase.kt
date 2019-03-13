package com.powerincode.questionnaire_app.domain.uscases.profile.credential

import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.ResolvableApiException
import com.powerincode.questionnaire_app.domain.repository.CredentialRepository
import com.powerincode.questionnaire_app.domain.uscases.NoArgsUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 06/03/2019.
 */
class GetCredentialUseCase @Inject constructor(private val credentialRepository : CredentialRepository) :
    NoArgsUseCase<GetCredentialUseCase.CredentialAvailability>() {

    override suspend fun run(param : None) : CredentialAvailability {
        return try {
            val credential = credentialRepository.getCredential()
            CredentialAvailability.Available(credential)
        } catch (e : ResolvableApiException) {
            if (e.statusCode == CommonStatusCodes.SIGN_IN_REQUIRED) {
                CredentialAvailability.Unavailable(e)
            } else {
                CredentialAvailability.NeedResolution(e)
            }
        }
    }

    sealed class CredentialAvailability {
        class Available(val credential : Credential) : CredentialAvailability()
        class NeedResolution(val exception : ResolvableApiException) : CredentialAvailability()
        class Unavailable(val exception : ResolvableApiException) : CredentialAvailability()

    }
}