package com.powerincode.questionnaire_app.domain.uscases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * Created by powerman23rus on 04/03/2019.
 */

interface UseCase

abstract class BaseUseCase<in Param, out Type> : UseCase {

    protected abstract suspend fun run(param : Param) : Type

    suspend operator fun invoke(param : Param) : Type {
        return withContext(Dispatchers.IO) { run(param) }
    }

    fun block(param : Param) : Type = runBlocking {
        run(param)
    }

    protected fun wrapNone(block : () -> Unit) : None {
        block()
        return None()
    }

    class None
}