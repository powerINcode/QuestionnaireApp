package com.powerincode.questionnaire_app.data.realtimedatabase.database

import com.powerincode.questionnaire_app.data.realtimedatabase.dao.users.UsersDaoImpl
import javax.inject.Inject

/**
 * Created by powerman23rus on 15/03/2019.
 */
class RealTimeDatabaseProviderImpl @Inject constructor(override val users : UsersDaoImpl) :
    RealTimeDatabaseProvider