package com.powerincode.questionnaire_app.core.extensions.common

import android.content.res.Resources

/**
 * Created by powerman23rus on 26/02/2019.
 */

fun Int.toDp() : Float {
    return this * Resources.getSystem().displayMetrics.density
}