package com.powerincode.questionnaire_app.domain.repository

import com.google.android.gms.auth.api.credentials.Credential

/**
 * Created by powerman23rus on 07/03/2019.
 */
interface CredentialRepository {
    suspend fun getCredential() : Credential
    suspend fun saveCredential(id : String, email : String, name : String?, password : String?, accountType : String?)
}