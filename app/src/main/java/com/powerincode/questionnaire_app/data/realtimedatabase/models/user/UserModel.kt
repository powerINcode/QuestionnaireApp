package com.powerincode.questionnaire_app.data.realtimedatabase.models.user

/**
 * Created by powerman23rus on 15/03/2019.
 */
data class UserModel(
    val id : String,
    val name : String?,
    val email : String?,
    val avatarUrl : String?,
    val isSignedViaEmail : Boolean
) {

    constructor() : this("", null, null, null, false)

    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val EMAIL = "email"
        const val IS_SIGNED_VIA_EMAIL = "isSignedViaEmail"
        const val AVATAR_URL = "avatarUrl"
    }
}