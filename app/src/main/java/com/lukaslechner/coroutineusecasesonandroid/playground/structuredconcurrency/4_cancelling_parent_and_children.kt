package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val scope = CoroutineScope(Dispatchers.Default)

    scope.launch {
        delay(1000)
        println("GETZ.<top>.main--> Coroutine 1 completed")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException)
            println("GETZ.<top>.main--> Coroutine 1 was cancelled")
    }

    scope.launch {
        delay(1000)
        println("GETZ.<top>.main--> Coroutine 2 completed")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException)
            println("GETZ.<top>.main--> Coroutine 2 was cancelled")
    }


    scope.coroutineContext[Job]!!.cancelAndJoin()
}