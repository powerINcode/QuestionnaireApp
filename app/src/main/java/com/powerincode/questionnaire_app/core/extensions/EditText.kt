package com.powerincode.questionnaire_app.core.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Created by powerman23rus on 27/02/2019.
 */

fun EditText.afterTextChanged(block : (String?) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s : Editable?) {

        }

        override fun beforeTextChanged(s : CharSequence?, start : Int, count : Int, after : Int) {

        }

        override fun onTextChanged(s : CharSequence?, start : Int, before : Int, count : Int) {
            block(s?.toString())
        }

    })
}