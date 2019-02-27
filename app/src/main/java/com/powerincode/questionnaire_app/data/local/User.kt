package com.powerincode.questionnaire_app.data.local

import android.support.annotation.DrawableRes
import com.google.android.gms.auth.api.credentials.Credential
import com.powerincode.questionnaire_app.BuildConfig

/**
 * Created by powerman23rus on 26/02/2019.
 */
data class User(
    val firstName : String,
    val lastName : String, @DrawableRes val avatarId : Int,
    val credentialTag : String = BuildConfig.APPLICATION_ID
) {
    fun toCredentional() : Credential {
        return Credential.Builder(credentialTag)
            .setName("$firstName $lastName")
            .build()
    }
}