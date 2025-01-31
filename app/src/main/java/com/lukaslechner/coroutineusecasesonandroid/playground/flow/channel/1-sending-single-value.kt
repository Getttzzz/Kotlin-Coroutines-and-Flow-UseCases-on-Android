package com.lukaslechner.coroutineusecasesonandroid.playground.flow.channel

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * This is an example of sending a value from one coroutine to another.
 * But how to send multiple values from one coroutine to another?
 * Here's why Channel exists.
 * */
suspend fun main(): Unit = coroutineScope {
    val deferred = async {
        delay(100)
        123
    }

    launch {
        val result = deferred.await()
        println("GETZ.<top>.main ---> result=$result")
    }
}