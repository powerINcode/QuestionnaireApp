package com.powerincode.questionnaire_app.di.modules.app.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.powerincode.questionnaire_app.data.api.TempService.TempService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by powerman23rus on 21/02/2019.
 */
@Module
object NetworkModule {
    const val BASE_URL = "https://jsonplaceholder.typicode.com"

    @Singleton
    @JvmStatic
    @Provides
    fun provideTemService(retrofit : Retrofit): TempService {
        return retrofit.create(TempService::class.java)
    }

    @Singleton
    @JvmStatic
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}