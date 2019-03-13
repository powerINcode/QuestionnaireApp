package com.powerincode.questionnaire_app.domain.uscases.auth

import com.google.firebase.auth.*
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.firebase.await
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.EqualityRule
import com.powerincode.questionnaire_app.domain.uscases.BaseUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateEmailUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateNameUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidatePasswordUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 05/03/2019.
 */
class SignUpUseCase @Inject constructor(
    private val validateName : ValidateNameUseCase,
    private val validateEmail : ValidateEmailUseCase,
    private val validatePassword : ValidatePasswordUseCase,
    private val firebaseAuth : FirebaseAuth
) : BaseUseCase<SignUpUseCase.SignUpParam, SignUpUseCase.SignUpResult>() {
    override suspend fun run(param : SignUpParam) : SignUpResult {
        val name = param.name
        val email = param.email
        val password = param.password
        val confirmPassword = param.confirmPassword

        try {
            val nameErrors = validateName(name)
            if (nameErrors.isNotEmpty()) {
                return SignUpResult.NameError(nameErrors)
            }

            val emailErrors = validateEmail(email)
            if (emailErrors.isNotEmpty()) {
                return SignUpResult.EmailError(emailErrors)
            }

            val passwordErrors = validatePassword(password)
            if (passwordErrors.isNotEmpty()) {
                return SignUpResult.PasswordError(passwordErrors)
            }

            val confirmPasswordErrors = validatePassword(confirmPassword)
            if (confirmPasswordErrors.isNotEmpty()) {
                return SignUpResult.PasswordError(confirmPasswordErrors)
            }

            val passwordsEquality =
                EqualityRule(password, confirmPassword, R.string.error_signup_password_not_equals).validate()
                    ?.messageId
            if (passwordsEquality != null) {
                return SignUpResult.PasswordEqualityError(nameErrors)
            }

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

    class SignUpParam(
        val name : String?,
        val email : String?,
        val password : String?,
        val confirmPassword : String?
    )

    sealed class SignUpResult(val errors : List<RuleError>) {
        class NameError(errors : List<RuleError>) : SignUpResult(errors)
        class EmailError(errors : List<RuleError>) : SignUpResult(errors)
        class PasswordError(errors : List<RuleError>) : SignUpResult(errors)
        class PasswordEqualityError(errors : List<RuleError>) : SignUpResult(errors)
        object UserNotCreatedError : SignUpResult(emptyList())
        object Success : SignUpResult(emptyList())
    }
}