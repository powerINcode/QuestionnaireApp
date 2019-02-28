package com.powerincode.questionnaire_app.core.extensions.views

import android.view.View
import android.view.ViewTreeObserver

/**
 * Created by powerman23rus on 25/02/2019.
 */

inline var View.isVisible : Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.INVISIBLE
    }

inline fun View.doOnLayout(crossinline block : (view : View) -> Unit) {
    this.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            block(this@doOnLayout)
        }
    })
}