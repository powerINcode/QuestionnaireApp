package com.powerincode.questionnaire_app.data.realtimedatabase.dao

import com.google.firebase.database.DatabaseReference

/**
 * Created by powerman23rus on 15/03/2019.
 */
abstract class BaseDao(protected val db : DatabaseReference) {
    protected abstract val root : String
    protected val node : DatabaseReference get() = db.child(root)

    protected fun <T : Any> createChild(block : (String) -> T ) : T {
        val id = node.push().key!!
        val model = block(id)
        node.child(id).setValue(model)

        return model
    }

}