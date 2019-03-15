package com.powerincode.questionnaire_app.data.repository

import com.powerincode.questionnaire_app.data.realtimedatabase.dao.users.models.SaveUserParams
import com.powerincode.questionnaire_app.data.realtimedatabase.database.RealTimeDatabaseProvider
import com.powerincode.questionnaire_app.data.realtimedatabase.models.user.UserModel
import com.powerincode.questionnaire_app.domain.repository.RemoteUserRepository
import javax.inject.Inject

class RemoteUserRepositoryImpl @Inject constructor(private val db : RealTimeDatabaseProvider) :
    RemoteUserRepository {

    override suspend fun getUser(id : String) : UserModel? {
        return db.users.getUser(id)
    }

    override fun saveUser(params : SaveUserParams) : UserModel {
        return db.users.saveUser(params)
    }
}