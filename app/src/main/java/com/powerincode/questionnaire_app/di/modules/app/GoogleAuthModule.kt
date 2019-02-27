package com.powerincode.questionnaire_app.di.modules.app

import android.content.Context
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.CredentialsClient
import com.google.android.gms.auth.api.credentials.CredentialsOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by powerman23rus on 25/02/2019.
 */

@Module
class GoogleAuthModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideGoogleSignInClient(context : Context, signedOptions : GoogleSignInOptions) : GoogleSignInClient {
        return GoogleSignIn.getClient(context, signedOptions)
    }

    @Singleton
    @Provides
    fun provideCredentialsClient(context : Context) : CredentialsClient {
        return Credentials.getClient(context, CredentialsOptions.Builder().forceEnableSaveDialog().build())
    }

    @Singleton
    @Provides
    fun provideGoogleSignedOptions() : GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }
}