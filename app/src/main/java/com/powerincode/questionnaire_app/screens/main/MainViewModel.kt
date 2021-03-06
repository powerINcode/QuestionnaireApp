package com.powerincode.questionnaire_app.screens.main

import android.util.Log
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.CredentialsClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.firebase.auth.FirebaseAuth
import com.powerincode.questionnaire_app.core.extensions.firebase.await
import com.powerincode.questionnaire_app.core.livedata.LiveEvent
import com.powerincode.questionnaire_app.core.livedata.MutableLiveEvent
import com.powerincode.questionnaire_app.domain.uscases.auth.GetCredentialUseCase
import com.powerincode.questionnaire_app.domain.uscases.profile.ClearProfileUseCase
import com.powerincode.questionnaire_app.domain.uscases.profile.GetProfileUseCase
import com.powerincode.questionnaire_app.screens._base.viewmodel.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import retrofit2.Call
import retrofit2.HttpException
import javax.inject.Inject


/**
 * Created by powerman23rus on 12/02/2019.
 */
class MainViewModel @Inject constructor(private val googleSignInClient : GoogleSignInClient,
                                        private val firebaseAuth : FirebaseAuth,
                                        private val getProfileUseCase : GetProfileUseCase,
                                        private val credentialsClient : CredentialsClient,
                                        private val getCredentialUseCase : GetCredentialUseCase,
                                        private var clearProfileUseCase : ClearProfileUseCase) : BaseViewModel() {

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
        request {
            getProfileUseCase.execute()?.let {
                val credential : Credential
                try {
                    credential = getCredentialUseCase.execute()
                    credentialsClient.disableAutoSignIn()
                    val a = 0
//                    credentialsClient.delete(credential).await()
                } catch (e : ResolvableApiException) {
                    // credentials not saved
                    val a = 0
                }

                if (it.isSignedViaEmail) {
                    firebaseAuth.signOut()
                } else {
                    googleSignInClient.signOut().await()
                }

                clearProfileUseCase.execute()

                _navigation.event = MainNavigation.NavigateToAuth
            }
        }
    }

    init {
        request {
            delay(300)
            getProfileUseCase.execute().apply {
                _message.event = toString()
            }
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
