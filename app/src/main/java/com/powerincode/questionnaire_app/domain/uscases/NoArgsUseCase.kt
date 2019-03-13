package com.powerincode.questionnaire_app.domain.uscases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * Created by powerman23rus on 04/03/2019.
 */

abstract class NoArgsUseCase<out Type> : BaseUseCase<BaseUseCase.None, Type>() {

    suspend operator fun invoke() : Type {
        return withContext(Dispatchers.IO) { run(None()) }
    }

    fun block() : Type = runBlocking {
        run(None())
    }
}