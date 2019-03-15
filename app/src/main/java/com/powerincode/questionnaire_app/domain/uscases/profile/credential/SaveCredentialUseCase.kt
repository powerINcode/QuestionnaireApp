package com.powerincode.questionnaire_app.domain.uscases.profile.credential

import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.data.realtimedatabase.models.user.UserModel
import com.powerincode.questionnaire_app.domain.repository.CredentialRepository
import com.powerincode.questionnaire_app.domain.uscases.BaseUseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidatePasswordUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 06/03/2019.
 */
class SaveCredentialUseCase @Inject constructor(
    private val credentialRepository : CredentialRepository,
    private val validatePassword : ValidatePasswordUseCase
) : BaseUseCase<SaveCredentialUseCase.SaveCredentialParam, SaveCredentialUseCase.SaveCredentialsResult>() {

    override suspend fun run(param : SaveCredentialParam) : SaveCredentialsResult {
        val id = param.id
        val email = param.email
        val name = param.name
        val password = param.password
        val avatarUrl = param.avatarUrl
        val accountType = param.accountType

        if (accountType == null) {
            val passwordErrors = validatePassword(password)
            if (passwordErrors.isNotEmpty()) {
                return SaveCredentialsResult.PasswordError(
                    passwordErrors
                )
            }
        }

        val user = UserModel(id, name, email, avatarUrl, accountType == null)
        try {
            credentialRepository.saveCredential(email, name, password, accountType)
        } catch (e : ResolvableApiException) {
            return SaveCredentialsResult.CredentialSavePromptError(
                user,
                e
            )
        } catch (e : ApiException) {
            if (e.statusCode == Status.RESULT_CANCELED.statusCode) {
                return SaveCredentialsResult.Canceled(
                    user
                )
            } else {
                throw e
            }
        }

        return SaveCredentialsResult.Success(
            user
        )
    }

    class SaveCredentialParam(
        val id : String,
        val email : String,
        val name : String?,
        val password : String?,
        val avatarUrl : String?,
        val accountType : String?
    )

    sealed class SaveCredentialsResult {
        class PasswordError(val errors : List<RuleError>) : SaveCredentialsResult()
        class CredentialSavePromptError(val user : UserModel, val exception : ResolvableApiException) :
            SaveCredentialsResult()

        class Success(val user : UserModel) : SaveCredentialsResult()
        class Canceled(val user : UserModel) : SaveCredentialsResult()
    }
}