package com.powerincode.questionnaire_app.screens.auth.signin.interactor

import com.google.android.gms.auth.api.credentials.Credential
import com.powerincode.questionnaire_app.data.local.User
import com.powerincode.questionnaire_app.domain.uscases.BaseUseCase.None
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
    private val validateEmail1 : ValidateEmailUseCase,
    private val validatePassword1 : ValidatePasswordUseCase,
    private val getCredential1 : GetCredentialUseCase,
    private val saveCredential : SaveCredentialUseCase,
    private val resolveCredential : ResolveCredentialSignInUseCase,
    private val signIn : SignInUseCase,
    private val saveProfile1 : SaveProfileUseCase
) : SignInInteractor {

    override fun validateEmail(email : String?) = validateEmail1.block(email)
    override fun validatePassword(password : String?) = validatePassword1.block(password)

    override suspend fun signIn(email : String?, password : String?) : SignInUseCase.SignInResult {
       return signIn.execute(email, password)
    }

    override suspend fun getCredential() : Credential {
        return getCredential1(None())
    }

    override suspend fun saveCredential(
        id : String,
        email : String,
        name : String?,
        password : String?,
        accountType : String?
    ) : SaveCredentialUseCase.SaveCredentialsResult {
        return saveCredential.execute(id, email, name, password, accountType)
    }

    override suspend fun resolveSignInCredential(credential : Credential) : ResolveCredentialSignInUseCase.ResolveCredentialResult {
        return resolveCredential.execute(credential)
    }

    override fun saveProfile(user : User)  {
        saveProfile1.block(user)
    }
}