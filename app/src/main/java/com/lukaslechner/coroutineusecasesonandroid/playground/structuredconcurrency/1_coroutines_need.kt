package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val scope = CoroutineScope(Dispatchers.Default)

fun main() = runBlocking {
    val job = scope.launch {
        delay(100)
        println("GETZ.<top>.main--> completed!")
    }

    job.invokeOnCompletion {
        if (it is CancellationException) {
            println("GETZ.<top>.main--> coroutine was cancelled!")
        }
    }

    delay(50)
    onDestroy()
}

private fun onDestroy() {
    println("GETZ.<top>.onDestroy--> life time of scope ends")
    scope.cancel()
}