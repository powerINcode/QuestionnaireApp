package com.powerincode.questionnaire_app.core.extensions.firebase.realtimedatabase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Created by powerman23rus on 15/03/2019.
 */

suspend fun <T> DatabaseReference.await(modelClass : Class<T>) = suspendCancellableCoroutine<T?> { continuous ->
    addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot : DataSnapshot) {
            continuous.resume(dataSnapshot.getValue(modelClass))
        }

        override fun onCancelled(error : DatabaseError) {
            continuous.resumeWithException(error.toException())
        }
    })
}

suspend fun <T : Any> DatabaseReference.awaitList(modelClass : Class<T>) = suspendCancellableCoroutine<List<T>?> { continuous ->
    addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot : DataSnapshot) {
            val value = dataSnapshot.children.mapNotNull { it.getValue<T>(modelClass) }
            continuous.resume(value)
        }

        override fun onCancelled(error : DatabaseError) {
            continuous.resumeWithException(error.toException())
        }
    })
}