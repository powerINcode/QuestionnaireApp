package com.powerincode.questionnaire_app.screens.main

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.powerincode.questionnaire_app.core.livedata.LiveEvent
import com.powerincode.questionnaire_app.core.livedata.MutableLiveEvent
import com.powerincode.questionnaire_app.screens._base.viewmodel.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import retrofit2.Call
import retrofit2.HttpException
import javax.inject.Inject


/**
 * Created by powerman23rus on 12/02/2019.
 */
class MainViewModel @Inject constructor(private val googleSignInClient : GoogleSignInClient) : BaseViewModel() {

    private val _navigation : MutableLiveEvent<MainNavigation> = MutableLiveEvent()
    val navigation : LiveEvent<MainNavigation> = _navigation

    suspend fun player(name : String, table : Channel<Int>) {
        for (hits in table) {
            Log.e("Channels", "$name : $hits")
            delay(100)
            table.send(hits + 1)
        }
    }

    fun signOut() {
        googleSignInClient.signOut().addOnCompleteListener {
            _navigation.event = MainNavigation.NavigateToAuth
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
    }

    return deferred
}
