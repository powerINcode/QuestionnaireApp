package com.powerincode.questionnaire_app.data.preference

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.powerincode.questionnaire_app.data.local.User
import com.squareup.moshi.Moshi

/**
 * Created by powerman23rus on 12/02/2019.
 */
class PreferenceProvider(context : Context) {
    private val preferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val moshi = Moshi.Builder().build()

    var user : User?
        get () = fromJson(preferences.getString("user", null))
        set(value) = preferences.edit {
            putString("user", toJson(value))
        }


    private inline fun SharedPreferences.edit(block : SharedPreferences.Editor.() -> Unit) {
        val editor = this.edit()
        block(editor)
        editor.apply()
    }

    private inline fun <reified T> toJson(item : T) : String? {
        item ?: return null
        return moshi.adapter(T::class.java).toJson(item)
    }

    private inline fun <reified T : Any> fromJson(json : String?) : T? {
        json ?: return null
        return moshi.adapter(T::class.java).fromJson(json)
    }
}