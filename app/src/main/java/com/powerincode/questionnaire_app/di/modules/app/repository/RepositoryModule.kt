package com.powerincode.questionnaire_app.di.modules.app.repository

import com.powerincode.questionnaire_app.data.repository.CredentialRepositoryImpl
import com.powerincode.questionnaire_app.data.repository.RemoteUserRepositoryImpl
import com.powerincode.questionnaire_app.data.repository.UserRepositoryImpl
import com.powerincode.questionnaire_app.domain.repository.CredentialRepository
import com.powerincode.questionnaire_app.domain.repository.RemoteUserRepository
import com.powerincode.questionnaire_app.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created by powerman23rus on 07/03/2019.
 */
@Module
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindUserRepository(repo: UserRepositoryImpl) : UserRepository

    @Singleton
    @Binds
    abstract fun bindRemoteUserRepository(repo: RemoteUserRepositoryImpl) : RemoteUserRepository

    @Singleton
    @Binds
    abstract fun bindCredentialRepository(repo: CredentialRepositoryImpl) : CredentialRepository
}