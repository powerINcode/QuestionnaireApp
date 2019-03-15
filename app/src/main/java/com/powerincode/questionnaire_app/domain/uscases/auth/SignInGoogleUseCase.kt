package com.powerincode.questionnaire_app.domain.uscases.auth

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.powerincode.questionnaire_app.core.extensions.firebase.auth.await
import com.powerincode.questionnaire_app.domain.uscases.BaseUseCase
import javax.inject.Inject

/**
 * Created by powerman23rus on 05/03/2019.
 */
class SignInGoogleUseCase @Inject constructor(
    private val firebaseAuth : FirebaseAuth
) : BaseUseCase<GoogleSignInAccount, BaseUseCase.None>() {

    override suspend fun run(param : GoogleSignInAccount) : None {
        val credential = GoogleAuthProvider.getCredential(param.idToken, null)
        firebaseAuth.signInWithCredential(credential).await()

        return None()
    }
}