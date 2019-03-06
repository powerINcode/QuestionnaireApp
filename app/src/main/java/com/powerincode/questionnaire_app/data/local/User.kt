package com.powerincode.questionnaire_app.data.local

/**
 * Created by powerman23rus on 26/02/2019.
 */
data class User(
    val id : String,
    val name : String,
    val email : String,
    val isSignedViaEmail : Boolean
) {
    override fun toString() : String {
        return "$name($id) : $email"
    }
}