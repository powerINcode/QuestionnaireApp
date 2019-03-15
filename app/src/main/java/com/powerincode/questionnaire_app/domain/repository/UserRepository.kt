package com.powerincode.questionnaire_app.domain.repository

import com.powerincode.questionnaire_app.data.realtimedatabase.models.user.UserModel

/**
 * Created by powerman23rus on 07/03/2019.
 */
interface UserRepository {
    fun getUser() : UserModel?
    fun saveUser(user : UserModel)
    fun deleteUser()
}