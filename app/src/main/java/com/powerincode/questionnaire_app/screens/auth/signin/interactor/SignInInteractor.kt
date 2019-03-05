package com.powerincode.questionnaire_app.screens.auth.signin.interactor

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.domain.uscases.auth.SignInUseCase
import com.powerincode.questionnaire_app.domain.uscases.profile.SaveProfileUseCase

interface SignInInteractor {
    fun validateEmail(email : String?) : List<RuleError>
    fun validatePassword(password : String?) : List<RuleError>

    suspend fun signIn(
        email : String?,
        password : String?
    ) : SignInUseCase.SignInResult

    fun saveProfile(account : GoogleSignInAccount) : SaveProfileUseCase.SaveProfileResult
    fun saveProfile(account : FirebaseUser) : SaveProfileUseCase.SaveProfileResult
}