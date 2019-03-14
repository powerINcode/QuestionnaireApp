package com.powerincode.questionnaire_app.validation

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateNameUseCase
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NameValidationUnitTest {
    private val validator = ValidateNameUseCase()

    @Test
    fun name_validations_is_correct() {
        assertTrue(validator.block("Name").isEmpty())
    }

    @Test
    fun name_empty_error() {
        val errors = validator.block("")
        assertTrue(errors.size == 1)
        assertTrue(errors.first().messageId == R.string.error_validation_name_empty)
    }

    @Test
    fun name_null_error() {
        val errors = validator.block(null)
        assertTrue(errors.size == 1)
        assertTrue(errors.first().messageId == R.string.error_validation_name_empty)
    }
}
