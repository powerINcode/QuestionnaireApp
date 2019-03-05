package com.powerincode.questionnaire_app.domain.uscases.auth

import com.google.firebase.auth.*
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.firebase.await
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.domain.uscases.UseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateEmailUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateNameUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidatePasswordUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 05/03/2019.
 */
class SignUpUseCase @Inject constructor(
    private val validateNameUseCase : ValidateNameUseCase,
    private val validateEmailUseCase : ValidateEmailUseCase,
    private val validatePasswordUseCase : ValidatePasswordUseCase,
    private val firebaseAuth : FirebaseAuth
) : UseCase {

    suspend fun execute(name : String?,
                        email : String?,
                        password : String?,
                        confirmPassword : String?) : SignUpResult {
        try {
            val nameErrors = validateNameUseCase.execute(name)
            if (nameErrors.isNotEmpty()) return SignUpResult.NameError(
                nameErrors
            )

            val emailErrors = validateEmailUseCase.execute(email)
            if (emailErrors.isNotEmpty()) return SignUpResult.EmailError(
                emailErrors
            )

            val passwordErrors = validatePasswordUseCase.execute(password)
            if (passwordErrors.isNotEmpty()) return SignUpResult.PasswordError(
                passwordErrors
            )

            val confirmPasswordErrors = validatePasswordUseCase.execute(confirmPassword)
            if (confirmPasswordErrors.isNotEmpty()) return SignUpResult.PasswordError(
                confirmPasswordErrors
            )

            val passwordsEquality = validatePasswordUseCase.validateEquality(password, confirmPassword)
            if (passwordsEquality.isNotEmpty()) return SignUpResult.PasswordEqualityError(
                nameErrors
            )

            val user = firebaseAuth.createUserWithEmailAndPassword(email!!, password!!).await()?.user
                ?: return SignUpResult.UserNotCreatedError

            val updateUserRequest = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()

            user.updateProfile(updateUserRequest).await()

            user.sendEmailVerification().await()
            return SignUpResult.Success
        } catch (e : FirebaseAuthWeakPasswordException) {
            return SignUpResult.PasswordError(
                listOf(
                    RuleError(R.string.error_validation_password_incorrect)
                )
            )
        } catch (e : FirebaseAuthInvalidCredentialsException) {
            return SignUpResult.EmailError(
                listOf(
                    RuleError(R.string.error_validation_email_format_incorrect)
                )
            )
        } catch (e : FirebaseAuthUserCollisionException) {
            return SignUpResult.EmailError(
                listOf(
                    RuleError(R.string.error_signin_email_already_use)
                )
            )
        }
    }

    sealed class SignUpResult(val errors : List<RuleError>) {
        class NameError(errors : List<RuleError>) : SignUpResult(errors)
        class EmailError(errors : List<RuleError>) : SignUpResult(errors)
        class PasswordError(errors : List<RuleError>) : SignUpResult(errors)
        class PasswordEqualityError(errors : List<RuleError>) : SignUpResult(errors)
        object UserNotCreatedError : SignUpResult(emptyList())
        object Success : SignUpResult(emptyList())
    }
}