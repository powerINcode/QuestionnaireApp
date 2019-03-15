package com.powerincode.questionnaire_app.domain.repository

import com.powerincode.questionnaire_app.data.realtimedatabase.dao.users.models.SaveUserParams
import com.powerincode.questionnaire_app.data.realtimedatabase.models.user.UserModel

/**
 * Created by powerman23rus on 07/03/2019.
 */
interface RemoteUserRepository {
    suspend fun getUser(id : String) : UserModel?
    fun saveUser(params : SaveUserParams) : UserModel
}