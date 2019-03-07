package com.powerincode.questionnaire_app.data.repository

import com.powerincode.questionnaire_app.data.local.User
import com.powerincode.questionnaire_app.data.preference.PreferenceProvider
import com.powerincode.questionnaire_app.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val preferenceProvider : PreferenceProvider) :
    UserRepository {
    override fun getUser() : User? {
        return preferenceProvider.user
    }

    override fun saveUser(user : User) {
        preferenceProvider.user = user
    }

    override fun deleteUser() {
        preferenceProvider.user = null
    }
}