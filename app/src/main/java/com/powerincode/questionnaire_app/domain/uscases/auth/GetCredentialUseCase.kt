package com.powerincode.questionnaire_app.domain.uscases.auth

import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.CredentialRequest
import com.google.android.gms.auth.api.credentials.CredentialsClient
import com.google.android.gms.auth.api.credentials.IdentityProviders
import com.powerincode.questionnaire_app.core.extensions.firebase.await
import com.powerincode.questionnaire_app.domain.uscases.BaseUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 06/03/2019.
 */
class GetCredentialUseCase @Inject constructor(private val credentialsClient : CredentialsClient) :
    BaseUseCase<BaseUseCase.None, Credential>() {

    override suspend fun run(param : None) : Credential {
        val requestCredentials = CredentialRequest.Builder()
            .setPasswordLoginSupported(true)
            .setAccountTypes(IdentityProviders.GOOGLE)
            .build()

        val credential = credentialsClient.request(requestCredentials).await()?.credential
        return credential!!
    }
}