package com.powerincode.questionnaire_app.di.modules.app.data

import com.google.firebase.database.FirebaseDatabase
import com.powerincode.questionnaire_app.data.realtimedatabase.dao.users.UsersDao
import com.powerincode.questionnaire_app.data.realtimedatabase.dao.users.UsersDaoImpl
import com.powerincode.questionnaire_app.data.realtimedatabase.database.RealTimeDatabaseProvider
import com.powerincode.questionnaire_app.data.realtimedatabase.database.RealTimeDatabaseProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by powerman23rus on 15/03/2019.
 */

@Module
class BaseFirebaseDatabaseModule {
    @Singleton
    @Provides
    fun provideFirebaseDatabase() = FirebaseDatabase.getInstance().reference

}

@Module(includes = [BaseFirebaseDatabaseModule::class])
abstract class RealTimeDatabaseModule {
    @Singleton
    @Binds
    abstract fun bindUsersDao(dao : UsersDaoImpl) : UsersDao

    @Singleton
    @Binds
    abstract fun bindRealTimeDatabase(db : RealTimeDatabaseProviderImpl) : RealTimeDatabaseProvider

}