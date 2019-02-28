package com.powerincode.questionnaire_app.core.extensions.firebase

import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by powerman23rus on 28/02/2019.
 */

suspend fun <T> Task<T?>.await() = suspendCoroutine<T?> { cont ->
    addOnCompleteListener {
        if (isSuccessful) {
            cont.resume(this.result)
        } else {
            cont.resumeWithException(exception ?: RuntimeException("Unknown task exception"))
        }
    }
}