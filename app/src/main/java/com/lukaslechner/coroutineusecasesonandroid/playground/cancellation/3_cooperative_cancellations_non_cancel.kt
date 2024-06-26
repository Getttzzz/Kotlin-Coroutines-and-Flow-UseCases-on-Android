package com.lukaslechner.coroutineusecasesonandroid.playground.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

fun main() = runBlocking {

    val job = launch(Dispatchers.Default) {

        repeat(10) { index ->

           if (isActive){
               println("GETZ.<top>.main--> operation number $index")
               Thread.sleep(100)
           } else {
               //NonCancellable is a context element.
               //NonCancellable is a non-cancelable job that is always active.
               //It is designed for "withContext" function to prevent cancellation of code blocks
               //that need to be executed without cancellation.
               withContext(NonCancellable) {
                   delay(100)
                   println("GETZ.<top>.main--> Cleaning up some resources...")
                   throw CancellationException()
               }
           }

        }
    }

    delay(250)
    println("GETZ.<top>.main--> Cancelling coroutine")

    job.cancel()
}