package com.powerincode.questionnaire_app.screens.auth.signin.interactor

import com.google.android.gms.auth.api.credentials.Credential
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.data.local.User
import com.powerincode.questionnaire_app.domain.uscases.auth.ResolveCredentialSignInUseCase
import com.powerincode.questionnaire_app.domain.uscases.auth.SaveCredentialUseCase
import com.powerincode.questionnaire_app.domain.uscases.auth.SignInUseCase

interface SignInInteractor {
    fun validateEmail(email : String?) : List<RuleError>
    fun validatePassword(password : String?) : List<RuleError>

    suspend fun signIn(
        email : String?,
        password : String?
    ) : SignInUseCase.SignInResult

    suspend fun getCredential() : Credential
    suspend fun saveCredential(id : String, email : String, name : String?, password : String?, accountType : String?) : SaveCredentialUseCase.SaveCredentialsResult
    suspend fun resolveSignInCredential(credential : Credential) : ResolveCredentialSignInUseCase.ResolveCredentialResult

    fun saveProfile(user : User)
}