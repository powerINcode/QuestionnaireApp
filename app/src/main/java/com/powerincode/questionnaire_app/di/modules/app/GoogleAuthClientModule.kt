package com.powerincode.questionnaire_app.di.modules.app

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by powerman23rus on 25/02/2019.
 */

@Module
class GoogleAuthClientModule {

    @Singleton
    @Provides
    fun provideGoogleSignInClient(context : Context, signedOptions : GoogleSignInOptions) : GoogleSignInClient {
        return GoogleSignIn.getClient(context, signedOptions)
    }

    @Singleton
    @Provides
    fun provideGoogleSignedOptions() : GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }
}