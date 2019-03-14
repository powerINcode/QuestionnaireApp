package com.powerincode.questionnaire_app.validation

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidatePasswordUseCase
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PasswordValidationUnitTest {
    private val validator = ValidatePasswordUseCase()

    @Test
    fun password_validations_is_correct() {
        assertTrue(validator.block("Qwer1234").isEmpty())
    }

    @Test
    fun password_incorrect_format_error() {
        val errors = validator.block("qwe23")
        assertTrue(errors.size == 1)
        assertTrue(errors.first().messageId == R.string.error_validation_password_incorrect)
    }

    @Test
    fun password_empty_error() {
        val errors = validator.block("")
        assertTrue(errors.size == 2)
        assertTrue(errors.first().messageId == R.string.error_validation_password_empty)
        assertTrue(errors.last().messageId == R.string.error_validation_password_incorrect)
    }

    @Test
    fun password_null_error() {
        val errors = validator.block(null)
        assertTrue(errors.size == 2)
        assertTrue(errors.first().messageId == R.string.error_validation_password_empty)
        assertTrue(errors.last().messageId == R.string.error_validation_password_incorrect)
    }
}
