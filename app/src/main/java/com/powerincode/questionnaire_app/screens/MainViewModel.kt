package com.powerincode.questionnaire_app.screens

import android.util.Log
import com.powerincode.questionnaire_app.data.api.TempService.TempService
import com.powerincode.questionnaire_app.screens.base.viewmodel.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import retrofit2.Call
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
 * Created by powerman23rus on 12/02/2019.
 */
class MainViewModel @Inject constructor(private val tempService : TempService) : BaseViewModel() {

    suspend fun player(name : String, table : Channel<Int>) {
        for (hits in table) {
            Log.e("Channels", "$name : $hits")
            delay(100)
            table.send(hits + 1)
        }
    }

    init {
        runBlocking {
            val table = Channel<Int>()
            launch { player("ping", table) }
            launch { player("pong", table) }

            table.send(0)
            delay(1000)
            coroutineContext.cancelChildren()
        }

//        launch {
//            delay(1000)
////            val response = client.newCall(request).deffered().await()
//            val response =  tempService.getPosts2().deffered().await()
////            val a = response.size
//            val b = 0
////            launch(Dispatchers.IO) {
////                call.execute(object : MyCall.MyCallEvent {
////                    override fun onSuccess(message : String) {
////                        _message.postEvent(message)
////                    }
////
////                    override fun onError() {
////                        _message.event = "Error"
////                    }
////                })
////            }
//
////            _message.event = withContext(Dispatchers.IO) { call.await() }
////            try {
////                _message.event = call.deffered().await()
////            } catch (e : CancellationException) {
////                val a = 0
////            }
//
////            tempService.getWithLatence().await()
//
//
//        }
    }

}

class MyCall() {
    var isCancelled = false
    fun execute(e : MyCallEvent) {
        Thread.sleep(2000)
        if (!isCancelled) {
            e.onSuccess("Complete")
        }
    }

    fun cancel() {
        isCancelled = true
    }

    interface MyCallEvent {
        fun onSuccess(message : String)
        fun onError()
    }
}

suspend fun MyCall.await() : String {
    return suspendCoroutine { cont ->
        execute(object : MyCall.MyCallEvent {
            override fun onSuccess(message : String) {
                cont.resume(message)
            }

            override fun onError() {
                cont.resumeWithException(IllegalAccessError(""))
            }
        })
    }
}
suspend fun okhttp3.Call.deffered() : Deferred<String> {
    val deferred = CompletableDeferred<String>().apply {
        invokeOnCompletion {
            this@deffered.cancel()
        }
    }
    withContext(Dispatchers.IO) {
        Log.e("deffered", Thread.currentThread().name)
        deferred.complete(execute().body()!!.string())
    }

    return deferred
}

suspend fun <T> Call<T>.deffered() : Deferred<T> {
    val deferred = CompletableDeferred<T>().apply {
        invokeOnCompletion {
            this@deffered.cancel()
        }
    }
    withContext(Dispatchers.IO) {
        Log.e("deffered", Thread.currentThread().name)
        val response = execute()
        if (response.isSuccessful) {
            Thread.sleep(3000)
            deferred.complete(response.body()!!)
        }else {
            deferred.completeExceptionally(HttpException(response))
        }
//        enqueue(object : Callback<T> {
//            override fun onFailure(call : Call<T>, t : Throwable) {
//                Thread.sleep(3000)
//                deferred.completeExceptionally(t)
//            }
//
//            override fun onResponse(call : Call<T>, response : Response<T>) {
//                Thread.sleep(3000)
//                deferred.complete(response.body()!!)
//            }
//        })
    }

    return deferred
}
