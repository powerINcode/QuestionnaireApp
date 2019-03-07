package com.powerincode.questionnaire_app.domain.repository

import com.powerincode.questionnaire_app.data.local.User

/**
 * Created by powerman23rus on 07/03/2019.
 */
interface UserRepository {
    fun getUser() : User?
    fun saveUser(user : User)
    fun deleteUser()
}