package com.powerincode.questionnaire_app.data.api.TempService

import com.powerincode.questionnaire_app.data.api.TempService.response.Post
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by powerman23rus on 20/02/2019.
 */
interface TempService {
    @GET("/posts")
    fun getPosts(): Deferred<List<Post>>

    @GET("/posts")
    fun getPosts2(): Call<List<Post>>

    @GET("/200?sleep=5000")
    fun getWithLatence() : Deferred<Unit>

    @GET("/200?sleep=5000")
    fun getWithLatence2() : Call<Unit>
}