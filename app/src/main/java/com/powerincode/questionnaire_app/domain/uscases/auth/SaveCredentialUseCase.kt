package com.powerincode.questionnaire_app.domain.uscases.auth

import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.CredentialsClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.powerincode.questionnaire_app.core.extensions.firebase.await
import com.powerincode.questionnaire_app.core.validators.errors.RuleError
import com.powerincode.questionnaire_app.data.local.User
import com.powerincode.questionnaire_app.domain.uscases.UseCase
import com.powerincode.questionnaire_app.domain.uscases.validation.ValidatePasswordUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 06/03/2019.
 */
class SaveCredentialUseCase @Inject constructor(
    private val credentialsClient : CredentialsClient,
    private val validatePasswordUseCase : ValidatePasswordUseCase
) : UseCase {
    suspend fun execute(id : String, email : String, name : String?, password : String?, accountType : String?) : SaveCredentialsResult {
        val credentialBuilder = Credential.Builder(email)

        if (accountType != null) {
            credentialBuilder.setAccountType(accountType)
                .setName(name)
        } else {
            val passwordErrors = validatePasswordUseCase.execute(password)
            if (passwordErrors.isEmpty()) {
                credentialBuilder
                    .setAccountType(null)
                    .setPassword(password!!)
            } else {
                return SaveCredentialsResult.PasswordError(passwordErrors)
            }
        }

        val user = User(id, name ?: "", email, accountType == null)
        val requestSaveCredentials = credentialBuilder.build()
        try {
            credentialsClient.save(requestSaveCredentials).await()
        } catch (e : ResolvableApiException) {
            return SaveCredentialsResult.CredentialSavePromptError(user, e)
        } catch (e : ApiException) {
            if (e.statusCode == Status.RESULT_CANCELED.statusCode) {
                return SaveCredentialsResult.Canceled(user)
            } else {
                throw e
            }
        }

        return SaveCredentialsResult.Success(user)
    }

    sealed class SaveCredentialsResult {
        class PasswordError(val errors : List<RuleError>) : SaveCredentialsResult()
        class CredentialSavePromptError(val user : User, val exception : ResolvableApiException) : SaveCredentialsResult()
        class Success(val user : User) : SaveCredentialsResult()
        class Canceled(val user : User) : SaveCredentialsResult()
    }
}