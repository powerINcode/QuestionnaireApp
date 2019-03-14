package com.powerincode.questionnaire_app.domain.uscases.validation

import android.support.v4.util.PatternsCompat
import com.powerincode.questionnaire_app.R
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.core.validators.rules.MatchRegexRule
import com.powerincode.questionnaire_app.core.validators.rules.NotEmptyRule
import javax.inject.Inject

/**
 * Created by powerman23rus on 04/03/2019.
 */
class ValidateEmailUseCase @Inject constructor() : BaseValidationUseCase<String?>() {
    override suspend fun run(param : String?) : List<RuleError> {
        return applyRules(
            NotEmptyRule(param, R.string.error_validation_email_empty),
            MatchRegexRule(param, PatternsCompat.EMAIL_ADDRESS.toRegex(), R.string.error_validation_email_format_incorrect)
        )
    }
}