package com.powerincode.questionnaire_app.domain.auth.signin

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.firebase.await
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.domain.auth.common.usecase.ValidateEmailUseCase
import com.powerincode.questionnaire_app.domain.auth.common.usecase.ValidatePasswordUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 04/03/2019.
 */
class SignInInteractorImpl @Inject constructor(
    private val validateEmailUseCase : ValidateEmailUseCase,
    private val validatePasswordUseCase : ValidatePasswordUseCase,
    private val firebaseAuth : FirebaseAuth
) : SignInInteractor {

    override fun validateEmail(email : String?) = validateEmailUseCase.execute(email)
    override fun validatePassword(password : String?) = validatePasswordUseCase.execute(password)

    override suspend fun signIn(email : String?, password : String?) : SignInResult {
        try {
            val emailErrors = validateEmail(email)
            if (emailErrors.isNotEmpty()) return SignInResult.EmailError(emailErrors)

            val passwordErrors = validatePassword(password)
            if (passwordErrors.isNotEmpty()) return SignInResult.PasswordError(passwordErrors)

            val user = firebaseAuth.signInWithEmailAndPassword(email!!, password!!).await()?.user
                ?: return SignInResult.UserNotSignInError(listOf(RuleError(R.string.error_signin_firebase_error)))

            return SignInResult.Success(user)
        } catch (e : FirebaseAuthInvalidUserException) {
            return SignInResult.UserNotSignInError(listOf(RuleError(R.string.error_signin_invalid_user)))
        } catch (e : FirebaseAuthInvalidCredentialsException) {
            return SignInResult.PasswordError(listOf(RuleError(R.string.error_signin_invalid_password)))
        }
    }
}