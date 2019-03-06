package com.powerincode.questionnaire_app.domain.uscases.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.firebase.await
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.domain.uscases.UseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateEmailUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidatePasswordUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 05/03/2019.
 */
class SignInUseCase @Inject constructor(
    private val validateEmailUseCase : ValidateEmailUseCase,
    private val validatePasswordUseCase : ValidatePasswordUseCase,
    private val firebaseAuth : FirebaseAuth
) : UseCase {

    suspend fun execute(email : String?, password : String?) : SignInResult {
        try {
            val emailErrors = validateEmailUseCase.execute(email)
            if (emailErrors.isNotEmpty()) return SignInUseCase.SignInResult.EmailError(
                emailErrors
            )

            val passwordErrors = validatePasswordUseCase.execute(password)
            if (passwordErrors.isNotEmpty()) return SignInUseCase.SignInResult.PasswordError(
                passwordErrors
            )

            val user = firebaseAuth.signInWithEmailAndPassword(email!!, password!!).await()?.user
                ?: return SignInUseCase.SignInResult.UserNotSignInError(
                    listOf(RuleError(R.string.error_signin_firebase_error))
                )

            return SignInUseCase.SignInResult.Success(user, password)
        } catch (e : FirebaseAuthInvalidUserException) {
            return SignInUseCase.SignInResult.UserNotSignInError(
                listOf(RuleError(R.string.error_signin_invalid_user))
            )
        } catch (e : FirebaseAuthInvalidCredentialsException) {
            return SignInUseCase.SignInResult.PasswordError(
                listOf(
                    RuleError(R.string.error_signin_invalid_password)
                )
            )
        }
    }

    sealed class SignInResult(val errors : List<RuleError>) {
        class EmailError(errors : List<RuleError>) : SignInResult(errors)
        class PasswordError(errors : List<RuleError>) : SignInResult(errors)
        class UserNotSignInError(errors : List<RuleError>) : SignInResult(errors)
        class Success(val user : FirebaseUser, val password : String) : SignInResult(emptyList())
    }
}