package com.lukaslechner.coroutineusecasesonandroid.playground.cancellation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val job = launch {
        repeat(10) {index ->
            println("GETZ.<top>.main--> operation number $index")
            delay(100)
        }
    }

    delay(250)
    println("GETZ.<top>.main--> Cancelling Coroutine")
    job.cancel()
}