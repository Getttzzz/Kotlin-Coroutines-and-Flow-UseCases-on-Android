package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main() {


    //This is live example of how to NOT to do.
    // Try-Catch block should be in each coroutine.
    val scope = CoroutineScope(Job())
    scope.launch {
        try {
            launch {
                functionThatThrows()
            }
        } catch (exception: Exception) {
            println("GETZ.<top>.main--> caught $exception")
        }
    }

    Thread.sleep(100)
}

private fun functionThatThrows() {
    throw RuntimeException()
}