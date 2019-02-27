package com.powerincode.questionnaire_app.core.extensions

import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * Created by powerman23rus on 26/02/2019.
 */

inline fun Fragment.toast(@StringRes resId : Int) = toast(getString(resId))
inline fun Fragment.toast(message : String) =  Toast.makeText(context, message, Toast.LENGTH_SHORT).show()