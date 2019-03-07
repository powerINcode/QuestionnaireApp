package com.powerincode.questionnaire_app.data.repository

import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.CredentialRequest
import com.google.android.gms.auth.api.credentials.CredentialsClient
import com.google.android.gms.auth.api.credentials.IdentityProviders
import com.powerincode.questionnaire_app.core.extensions.firebase.await
import com.powerincode.questionnaire_app.domain.repository.CredentialRepository
import javax.inject.Inject

class CredentialRepositoryImpl @Inject constructor(private val credentialsClient : CredentialsClient) :
    CredentialRepository {
    override suspend fun getCredential() : Credential {
        val requestCredentials = CredentialRequest.Builder()
            .setPasswordLoginSupported(true)
            .setAccountTypes(IdentityProviders.GOOGLE)
            .build()

        val credential = credentialsClient.request(requestCredentials).await()?.credential
        return credential!!
    }

    override suspend fun saveCredential(
        id : String,
        email : String,
        name : String?,
        password : String?,
        accountType : String?
    ) {
        val credentialBuilder = Credential.Builder(email)

        if (accountType != null) {
            credentialBuilder.setAccountType(accountType)
                .setName(name)
        } else {
            credentialBuilder
                .setAccountType(null)
                .setPassword(password!!)
        }

        val requestSaveCredentials = credentialBuilder.build()
        credentialsClient.save(requestSaveCredentials).await()
    }
}