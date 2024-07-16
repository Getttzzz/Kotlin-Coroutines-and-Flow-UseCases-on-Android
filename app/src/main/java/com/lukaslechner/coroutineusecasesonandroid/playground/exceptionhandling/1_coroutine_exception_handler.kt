package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main() {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("GETZ.<top>.main--> throwable=$throwable")
    }

    val scope = CoroutineScope(Job() + exceptionHandler)

    scope.launch {
       throw RuntimeException()
    }
    // Option+Command+T -> Surround with. Very useful hotkey.
    Thread.sleep(100)
}

private fun functionThatThrows() {
    throw RuntimeException()
}