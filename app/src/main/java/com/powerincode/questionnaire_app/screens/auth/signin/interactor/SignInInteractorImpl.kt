package com.powerincode.questionnaire_app.screens.auth.signin.interactor

import com.google.android.gms.auth.api.credentials.Credential
import com.powerincode.questionnaire_app.data.local.User
import com.powerincode.questionnaire_app.domain.uscases.auth.GetCredentialUseCase
import com.powerincode.questionnaire_app.domain.uscases.auth.ResolveCredentialSignInUseCase
import com.powerincode.questionnaire_app.domain.uscases.auth.SaveCredentialUseCase
import com.powerincode.questionnaire_app.domain.uscases.auth.SignInUseCase
import com.powerincode.questionnaire_app.domain.uscases.profile.SaveProfileUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateEmailUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidatePasswordUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 04/03/2019.
 */
class SignInInteractorImpl @Inject constructor(
    private val validateEmailUseCase : ValidateEmailUseCase,
    private val validatePasswordUseCase : ValidatePasswordUseCase,
    private val getCredentialUseCase : GetCredentialUseCase,
    private val saveCredentialUseCase : SaveCredentialUseCase,
    private val resolveCredentialSignInUseCase : ResolveCredentialSignInUseCase,
    private val signInUseCase : SignInUseCase,
    private val saveProfileUseCase : SaveProfileUseCase
) : SignInInteractor {

    override fun validateEmail(email : String?) = validateEmailUseCase.execute(email)
    override fun validatePassword(password : String?) = validatePasswordUseCase.execute(password)

    override suspend fun signIn(email : String?, password : String?) : SignInUseCase.SignInResult {
       return signInUseCase.execute(email, password)
    }

    override suspend fun getCredential() : Credential {
        return getCredentialUseCase.execute()
    }

    override suspend fun saveCredential(
        id : String,
        email : String,
        name : String?,
        password : String?,
        accountType : String?
    ) : SaveCredentialUseCase.SaveCredentialsResult {
        return saveCredentialUseCase.execute(id, email, name, password, accountType)
    }

    override suspend fun resolveSignInCredential(credential : Credential) : ResolveCredentialSignInUseCase.ResolveCredentialResult {
        return resolveCredentialSignInUseCase.execute(credential)
    }

    override fun saveProfile(user : User)  {
        return saveProfileUseCase.execute(user)
    }
}