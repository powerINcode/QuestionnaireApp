package com.powerincode.questionnaire_app.domain.uscases.profile

import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.data.local.User
import com.powerincode.questionnaire_app.data.preference.PreferenceProvider
import com.powerincode.questionnaire_app.domain.uscases.UseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateEmailUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidateNameUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 05/03/2019.
 */
class SaveProfileUseCase @Inject constructor(
    private val validateNameUseCase : ValidateNameUseCase,
    private val validateEmailUseCase : ValidateEmailUseCase,
    private val preferenceProvider : PreferenceProvider
) :
    UseCase {

    fun execute(id : String?, name : String?, email : String?, isSigninFromEmailAndPassword : Boolean) : SaveProfileResult {
        val errorsName = validateNameUseCase.execute(name)
        val errorsEmail = validateEmailUseCase.execute(email)

        return when {
            errorsName.isNotEmpty() -> SaveProfileResult.NameError(errorsName)
            errorsEmail.isNotEmpty() -> SaveProfileResult.EmailError(errorsEmail)
            else -> {
                val user = User(id, name!!, email!!, isSigninFromEmailAndPassword)
                preferenceProvider.user = user
                SaveProfileResult.Success(user)
            }
        }
    }

    sealed class SaveProfileResult(val errors : List<RuleError>) {
        class EmailError(errors : List<RuleError>) : SaveProfileResult(errors)
        class NameError(errors : List<RuleError>) : SaveProfileResult(errors)
        class Success(val user : User) : SaveProfileResult(emptyList())
    }
}