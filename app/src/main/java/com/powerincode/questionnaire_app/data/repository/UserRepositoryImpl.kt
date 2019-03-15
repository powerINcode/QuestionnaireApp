package com.powerincode.questionnaire_app.data.repository

import com.powerincode.questionnaire_app.data.preference.PreferenceProvider
import com.powerincode.questionnaire_app.data.realtimedatabase.models.user.UserModel
import com.powerincode.questionnaire_app.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val preferenceProvider : PreferenceProvider) :
    UserRepository {
    override fun getUser() : UserModel? {
        return preferenceProvider.user
    }

    override fun saveUser(user : UserModel) {
        preferenceProvider.user = user
    }

    override fun deleteUser() {
        preferenceProvider.user = null
    }
}