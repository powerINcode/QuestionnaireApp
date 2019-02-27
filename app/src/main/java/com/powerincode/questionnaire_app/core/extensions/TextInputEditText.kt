package com.powerincode.questionnaire_app.core.extensions

import android.support.design.widget.TextInputEditText
import android.widget.EditText

/**
 * Created by powerman23rus on 27/02/2019.
 */

var TextInputEditText.textString : String?
    get() = (this as EditText).text?.toString()
    set(value) {
        (this as EditText).setText(value)
    }

var TextInputEditText.textIfDifferent : String?
    get() = (this as EditText).text?.toString()
    set(value) {
        if (value != text?.toString()) {
            (this as EditText).setText(value)
        }
    }