package com.powerincode.questionnaire_app.domain.registration

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.extensions.firebase.await
import com.powerincode.questionnaire_app.core.validators.EmailValidator
import com.powerincode.questionnaire_app.core.validators.NameValidator
import com.powerincode.questionnaire_app.core.validators.PasswordValidator
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import javax.inject.Inject

/**
 * Created by powerman23rus on 01/03/2019.
 */
class RegistrationUseCaseImpl @Inject constructor(
    private val nameValidator : NameValidator,
    private val emailValidator : EmailValidator,
    private val passwordValidator : PasswordValidator,
    private val firebaseAuth : FirebaseAuth
) : RegistrationUseCase {

    override fun validateName(name : String?) = nameValidator.validate(name)
    override fun validateEmail(email : String?) = emailValidator.validate(email)
    override fun validatePassword(password : String?) = passwordValidator.validate(password)
    override fun validatePasswordEquality(password : String?, confirmPassword : String?) =
        passwordValidator.validateEquality(password, confirmPassword)

    override suspend fun register(
        name : String?,
        email : String?,
        password : String?,
        confirmPassword : String?
    ) : RegisterResult {
        try {
            val nameErrors = validateName(name)
            if (nameErrors.isNotEmpty()) return RegisterResult.NameError(nameErrors)

            val emailErrors = validateEmail(email)
            if (emailErrors.isNotEmpty()) return RegisterResult.EmailError(emailErrors)

            val passwordErrors = validatePassword(password)
            if (passwordErrors.isNotEmpty()) return RegisterResult.PasswordError(passwordErrors)

            val confirmPasswordErrors = validatePassword(confirmPassword)
            if (confirmPasswordErrors.isNotEmpty()) return RegisterResult.PasswordError(confirmPasswordErrors)

            val passwordsEquality = validatePasswordEquality(password, confirmPassword)
            if (passwordsEquality.isNotEmpty()) return RegisterResult.PasswordEqualityError(nameErrors)

            val user = firebaseAuth.createUserWithEmailAndPassword(email!!, password!!).await()?.user
                ?: return RegisterResult.UserNotCreatedError

            user.sendEmailVerification().await()
            return RegisterResult.Success
        } catch (e : FirebaseAuthWeakPasswordException) {
            return RegisterResult.PasswordError(listOf(RuleError(R.string.error_all_password_incorrect)))
        } catch (e : FirebaseAuthInvalidCredentialsException) {
            return RegisterResult.EmailError(listOf(RuleError(R.string.error_all_email_format_incorrect)))
        } catch (e : FirebaseAuthUserCollisionException) {
            return RegisterResult.EmailError(listOf(RuleError(R.string.error_signin_email_already_use)))
        }

    }
}