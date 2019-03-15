package com.powerincode.questionnaire_app.data.realtimedatabase.database

import com.powerincode.questionnaire_app.data.realtimedatabase.dao.users.UsersDaoImpl

interface RealTimeDatabaseProvider {
    val users : UsersDaoImpl
}