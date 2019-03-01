package com.powerincode.questionnaire_app.core.extensions.views

import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * Created by powerman23rus on 26/02/2019.
 */

fun Fragment.getStringOrNull(@StringRes resId : Int?) : String? {
    return if (resId != null) {
        getString(resId)
    } else {
        null
    }
}

fun Fragment.toast(@StringRes resId : Int) = toast(getString(resId))
fun Fragment.toast(message : String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()