package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

fun main() {

    val exceptionHandler = CoroutineExceptionHandler{coroutineContext, throwable ->
        println("GETZ.<top>.main--> caught exception $throwable")
    }


    // For regular job: when child fails, second child got cancelled.
//    val scope = CoroutineScope(Job() + exceptionHandler)

    // For SupervisorJob(): when child fails, second child keeps running.
    // And supervisor job is still alive after children are done.
    // aand we can start new coroutines in this scope.
    // aaand this is how "viewModelScope" works. It has supervisorJob under the hood.
    val scope = CoroutineScope(SupervisorJob() + exceptionHandler)

    scope.launch {
        println("GETZ.<top>.main--> Cor 1 start")
        delay(50)
        println("GETZ.<top>.main--> Cor 1 fails")
        throw RuntimeException()
    }


    val job2 = scope.launch {
        println("GETZ.<top>.main--> Cor 2 start")
        delay(500)
        println("GETZ.<top>.main--> Cor 2 completed")
    }

    job2.invokeOnCompletion {
        if (it is CancellationException)
            println("GETZ.<top>.main--> Cor 2 got cancelled")
    }

    Thread.sleep(1000)

    println("GETZ.<top>.main--> scope isActive=${scope.isActive}")
}