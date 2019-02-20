package com.powerincode.questionnaire_app.data.preference.api

import java.util.*

/**
 * Created by powerman23rus on 20/02/2019.
 */
object FakeNetwork {
    fun getData(): Boolean {
        Thread.sleep(1000)
        return Random().nextBoolean()
    }
}