package com.powerincode.questionnaire_app.data.realtimedatabase.dao.users

import com.google.firebase.database.DatabaseReference
import com.powerincode.questionnaire_app.core.extensions.firebase.realtimedatabase.await
import com.powerincode.questionnaire_app.data.realtimedatabase.dao.BaseDao
import com.powerincode.questionnaire_app.data.realtimedatabase.dao.users.models.SaveUserParams
import com.powerincode.questionnaire_app.data.realtimedatabase.models.user.UserModel
import javax.inject.Inject

/**
 * Created by powerman23rus on 15/03/2019.
 */
class UsersDaoImpl @Inject constructor(db : DatabaseReference) : BaseDao(db), UsersDao {
    override val root : String = "users"

    override suspend fun getUser(id : String) : UserModel? {
        return node.child(id).await(UserModel::class.java)
    }

    override fun saveUser(params : SaveUserParams) : UserModel {
//        val id = node.push().key!!
        val model = UserModel(params.id, params.name, params.email, params.avatarUrl, params.isSignedViaEmail)
        node.child(model.id).setValue(model)

        return model
//        return createChild { UserModel(params.id, params.name, params.email, params.avatarUrl, params.isSignedViaEmail) }
    }
}