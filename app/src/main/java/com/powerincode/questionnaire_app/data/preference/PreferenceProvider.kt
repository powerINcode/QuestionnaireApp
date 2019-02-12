package com.powerincode.questionnaire_app.data.preference

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by powerman23rus on 12/02/2019.
 */
class PreferenceProvider(context : Context) {
    private val preferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)


    var token : String
        get () = preferences.getString("token", "MY_SUPPER_TOKEN")!!
        set(value) = preferences.edit {
            putString("token", value)
        }


    private inline fun SharedPreferences.edit(block : SharedPreferences.Editor.() -> Unit) {
        val editor = this.edit()
        block(editor)
        editor.apply()
    }
}