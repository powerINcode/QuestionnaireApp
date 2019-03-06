package com.powerincode.questionnaire_app.domain.uscases.auth

import android.content.Context
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.IdentityProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.powerincode.questionnaire_app.core.extensions.firebase.await
import com.powerincode.questionnaire_app.domain.uscases.UseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 06/03/2019.
 */
class ResolveCredentialSignInUseCase @Inject constructor(private val context : Context) : UseCase {
    suspend fun execute(credential : Credential) : ResolveCredentialResult {
        return when(credential.accountType) {
            null -> ResolveCredentialResult.ManualSignIn(credential.id, credential.password!!)
            IdentityProviders.GOOGLE -> {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .setAccountName(credential.id)
                    .build()
                val client = GoogleSignIn.getClient(context, gso)

                val user = client.silentSignIn().await()
                ResolveCredentialResult.GoogleSignIn(user!!)
            }
            else -> {
               return ResolveCredentialResult.UnknownAccountType(credential.accountType!!)
            }
        }
    }

    sealed class ResolveCredentialResult {
        class ManualSignIn(val email : String, val password : String) : ResolveCredentialResult()
        class GoogleSignIn(val user : GoogleSignInAccount) : ResolveCredentialResult()
        class UnknownAccountType(val type : String) : ResolveCredentialResult()
    }
}