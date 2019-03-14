package com.powerincode.questionnaire_app.validation

import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateEmailUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class EmailValidationUnitTest {
    @Test
    fun email_validations_is_correct() {
        val validate = ValidateEmailUseCase()
        assertTrue(validate.block("test@gmail.com").isEmpty())
    }

    @Test
    fun email_empty_error() {
        val validate = ValidateEmailUseCase()
        val errors = validate.block("")
        assertEquals(errors.size, 2)
        assertEquals(errors.first().messageId, R.string.error_validation_email_empty)
        assertEquals(errors.last().messageId,
            R.string.error_validation_email_format_incorrect
        )
    }

    @Test
    fun email_without_name_error() {
        val validate = ValidateEmailUseCase()
        val errors = validate.block("@gmail.com")
        assertEquals(errors.size, 1)
        assertEquals(errors.first().messageId,
            R.string.error_validation_email_format_incorrect
        )
    }

    @Test
    fun email_without_dog_error() {
        val validate = ValidateEmailUseCase()
        val errors = validate.block("testgmail.com")
        assertEquals(errors.size, 1)
        assertEquals(errors.first().messageId,
            R.string.error_validation_email_format_incorrect
        )
    }

    @Test
    fun email_without_dot_error() {
        val validate = ValidateEmailUseCase()
        val errors = validate.block("test@gmailcom")
        assertEquals(errors.size, 1)
        assertEquals(errors.first().messageId,
            R.string.error_validation_email_format_incorrect
        )
    }
}
