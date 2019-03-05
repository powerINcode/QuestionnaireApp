package com.powerincode.questionnaire_app.screens.auth.signin.interactor

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
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
    private val signInUseCase : SignInUseCase,
    private val saveProfileUseCase : SaveProfileUseCase
) : SignInInteractor {

    override fun validateEmail(email : String?) = validateEmailUseCase.execute(email)
    override fun validatePassword(password : String?) = validatePasswordUseCase.execute(password)

    override suspend fun signIn(email : String?, password : String?) : SignInUseCase.SignInResult {
       return signInUseCase.execute(email, password)
    }

    override fun saveProfile(account : GoogleSignInAccount) : SaveProfileUseCase.SaveProfileResult {
        return saveProfileUseCase.execute(account.id, account.displayName, account.email, true)
    }

    override fun saveProfile(account : FirebaseUser) : SaveProfileUseCase.SaveProfileResult {
        return saveProfileUseCase.execute(account.providerId, account.displayName, account.email, true)
    }
}