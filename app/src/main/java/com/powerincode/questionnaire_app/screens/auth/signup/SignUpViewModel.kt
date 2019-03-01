package com.powerincode.questionnaire_app.screens.auth.signup

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.powerincode.questionnaire_app.core.validators.EmailValidator
import com.powerincode.questionnaire_app.core.validators.NameValidator
import com.powerincode.questionnaire_app.core.validators.PasswordValidator
import com.powerincode.questionnaire_app.screens._base.viewmodel.StateViewModel
import javax.inject.Inject

/**
 * Created by powerman23rus on 27/02/2019.
 */

class SignUpViewModel @Inject constructor(
    private val nameValidator : NameValidator,
    private val emailValidator : EmailValidator,
    private val passwordValidator : PasswordValidator
) : StateViewModel<SignUpState, SignUpNavigation>() {

    private val _name : MutableLiveData<String?> = MutableLiveData()
    val name : LiveData<String?> = _name

    private val _email : MutableLiveData<String?> = MutableLiveData()
    val email : LiveData<String?> = _email

    private val _password : MutableLiveData<String?> = MutableLiveData()
    val password : LiveData<String?> = _password

    private val _confirmPassword : MutableLiveData<String?> = MutableLiveData()
    val confirmPassword : LiveData<String?> = _confirmPassword

    //region errors
    private val _errorName : MutableLiveData<SignUpError.NameError> = MutableLiveData()
    val errorName : LiveData<SignUpError.NameError?> = _errorName

    private val _errorEmail : MutableLiveData<SignUpError.EmailError> = MutableLiveData()
    val errorEmail : LiveData<SignUpError.EmailError?> = _errorEmail

    private val _errorPassword : MutableLiveData<SignUpError.PasswordError> = MutableLiveData()
    val errorPassword : LiveData<SignUpError.PasswordError?> = _errorPassword

    private val _errorConfirmPassword : MutableLiveData<SignUpError.PasswordError> = MutableLiveData()
    val errorConfirmPassword : LiveData<SignUpError.PasswordError?> = _errorConfirmPassword
    //endregion

    fun onNameChange(name : String?) {
        _name.value = name
        handleNameError()
    }

    fun onEmailChange(email : String?) {
        _email.value = email
        handleEmailError()
    }

    fun onPasswordChange(password : String?) {
        _password.value = password
        handlePasswordError()
    }

    fun onConfirmPasswordChange(confirmPassword : String?) {
        _confirmPassword.value = confirmPassword
        handleConfirmPasswordError()
    }


    fun onSignUpClick() {
        handleNameError()
        handleEmailError()
        handlePasswordError()
        handleConfirmPasswordError()


        val isValid = _errorName.value == null &&
                _errorEmail.value == null &&
                _errorPassword.value == null &&
                _errorConfirmPassword.value == null

        if (isValid) {
            _message.event = "Success"
        }

//        nameValidator.validate(name)
//        firebaseAuth.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener { createUser ->
//            if (createUser.isSuccessful) {
//                _message.event = "Success"
//
//                val user = firebaseAuth.currentUser ?: return@addOnCompleteListener
//                _message.event = "Send verification"
//                user.sendEmailVerification().addOnCompleteListener { sendVerification ->
//                    if (sendVerification.isSuccessful) {
//                        _message.event = "Send verification success"
//                    } else {
//                        _message.event = "Send verification failed"
//                    }
//                }
//            } else {
//                _message.event = "Failed"
//            }
//        }

        // FirebaseAuthUserCollisionException: The email address is already in use by another account.
    }

//    private fun validateName() : List<RuleError> = nameValidator.validate(_name.value)
//    private fun validateEmail() : List<RuleError> = emailValidator.validate(_email.value)
//    private fun validatePassword() : List<RuleError> = passwordValidator.validate(_password.value)
//    private fun validateConfirmPassword() : List<RuleError> = passwordValidator.validate(_confirmPassword.value)
//    private fun validatePasswordsEquality() : List<RuleError> = passwordValidator.validateEquality(_password.value, _confirmPassword.value)

    private fun validateName() : SignUpError.NameError? {
        val messageId = nameValidator.validate(_name.value).firstOrNull()?.messageId
        return if(messageId != null) SignUpError.NameError(messageId) else null
    }

    private fun handleNameError() {
        _errorName.value = validateName()
    }

    private fun validateEmail() : SignUpError.EmailError? {
        val messageId = emailValidator.validate(_email.value).firstOrNull()?.messageId
        return if(messageId != null) SignUpError.EmailError(messageId) else null
    }

    private fun handleEmailError() {
        _errorEmail.value = validateEmail()
    }

    private fun validatePassword() : SignUpError.PasswordError? {
        val messageId = passwordValidator.validate(_password.value)
            .firstOrNull()?.messageId
        return if(messageId != null) SignUpError.PasswordError(messageId) else null
    }

    private fun handlePasswordError() {
        val validatePassword = validatePassword()
        val validatePasswordEquality = validatePasswordEquality()

        when {
            validatePassword != null -> _errorPassword.value = validatePassword
            else -> handlePasswordsEquality(validatePasswordEquality)
        }
    }

    private fun validateConfirmPassword() : SignUpError.PasswordError? {
        val messageId = passwordValidator.validate(_confirmPassword.value)
            .firstOrNull()?.messageId
        return if(messageId != null) SignUpError.PasswordError(messageId) else null
    }

    private fun handleConfirmPasswordError() {
        val validateConfirmPassword = validateConfirmPassword()
        val validatePasswordEquality = validatePasswordEquality()

        when {
            validateConfirmPassword != null -> _errorConfirmPassword.value = validateConfirmPassword
            else -> handlePasswordsEquality(validatePasswordEquality)
        }
    }

    private fun validatePasswordEquality() : SignUpError.PasswordError?{
        val messageId = passwordValidator.validateEquality(_password.value, _confirmPassword.value).firstOrNull()?.messageId
        return if(messageId != null) SignUpError.PasswordError(messageId) else null
    }

    private fun handlePasswordsEquality(error : SignUpError.PasswordError?) {
        _errorPassword.value = error
        _errorConfirmPassword.value = error
    }
}