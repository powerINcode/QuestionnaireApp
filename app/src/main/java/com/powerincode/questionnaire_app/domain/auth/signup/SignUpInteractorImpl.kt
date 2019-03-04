package com.powerincode.questionnaire_app.domain.auth.signup

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.firebase.await
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.domain.auth.common.usecase.ValidateEmailUseCase
import com.powerincode.questionnaire_app.domain.auth.common.usecase.ValidatePasswordUseCase
import com.powerincode.questionnaire_app.domain.auth.signup.usecase.ValidateNameUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 04/03/2019.
 */
class SignUpInteractorImpl @Inject constructor(private val validateEmailUseCase : ValidateEmailUseCase,
                                               private val validateNameUseCase : ValidateNameUseCase,
                                               private val validatePasswordUseCase : ValidatePasswordUseCase,
                                               private val firebaseAuth : FirebaseAuth) : SignUpInteractor {

    override fun validateName(name : String?) = validateNameUseCase.execute(name)
    override fun validateEmail(email : String?) = validateEmailUseCase.execute(email)
    override fun validatePassword(password : String?) = validatePasswordUseCase.execute(password)
    override fun validateConfirmPassword(password : String?) = validatePasswordUseCase.execute(password)
    override fun validatePasswordsEquality(password : String?, confirmPassword : String?) = validatePasswordUseCase.validateEquality(password, confirmPassword)

    override suspend fun register(
        name : String?,
        email : String?,
        password : String?,
        confirmPassword : String?
    ) : SignUpResult {
        try {
            val nameErrors = validateName(name)
            if (nameErrors.isNotEmpty()) return SignUpResult.NameError(nameErrors)

            val emailErrors = validateEmail(email)
            if (emailErrors.isNotEmpty()) return SignUpResult.EmailError(emailErrors)

            val passwordErrors = validatePassword(password)
            if (passwordErrors.isNotEmpty()) return SignUpResult.PasswordError(passwordErrors)

            val confirmPasswordErrors = validatePassword(confirmPassword)
            if (confirmPasswordErrors.isNotEmpty()) return SignUpResult.PasswordError(confirmPasswordErrors)

            val passwordsEquality = validatePasswordsEquality(password, confirmPassword)
            if (passwordsEquality.isNotEmpty()) return SignUpResult.PasswordEqualityError(nameErrors)

            val user = firebaseAuth.createUserWithEmailAndPassword(email!!, password!!).await()?.user
                ?: return SignUpResult.UserNotCreatedError

            user.sendEmailVerification().await()
            return SignUpResult.Success
        } catch (e : FirebaseAuthWeakPasswordException) {
            return SignUpResult.PasswordError(listOf(RuleError(R.string.error_all_password_incorrect)))
        } catch (e : FirebaseAuthInvalidCredentialsException) {
            return SignUpResult.EmailError(listOf(RuleError(R.string.error_all_email_format_incorrect)))
        } catch (e : FirebaseAuthUserCollisionException) {
            return SignUpResult.EmailError(listOf(RuleError(R.string.error_signin_email_already_use)))
        }

    }

}