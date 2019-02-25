package com.powerincode.questionnaire_app.core.extensions

import android.view.View
import android.view.ViewTreeObserver

/**
 * Created by powerman23rus on 25/02/2019.
 */

inline fun View.doOnLayout(crossinline block : (view : View) -> Unit) {
    this.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            block(this@doOnLayout)
        }
    })
}