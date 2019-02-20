package com.powerincode.questionnaire_app.core.livedata

import android.arch.lifecycle.LiveData

/**
 * Created by powerman23rus on 20/02/2019.
 */
open class LiveEvent<T> : LiveData<Event<T>>()