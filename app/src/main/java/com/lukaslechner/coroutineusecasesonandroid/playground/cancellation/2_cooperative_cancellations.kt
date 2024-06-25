package com.lukaslechner.coroutineusecasesonandroid.playground.cancellation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

fun main() = runBlocking {

    val job = launch(Dispatchers.Default) {

        repeat(10) { index ->

            yield() // or ensureActive() or if(isActive) {} else {throw CancellationException}

            println("GETZ.<top>.main--> operation number $index")
            Thread.sleep(100)
        }
    }

    delay(250)
    println("GETZ.<top>.main--> Cancelling coroutine")

    job.cancel()
}