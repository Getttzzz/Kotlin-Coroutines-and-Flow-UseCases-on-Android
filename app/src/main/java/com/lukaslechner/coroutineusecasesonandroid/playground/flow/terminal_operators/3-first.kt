package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

/**
 * Printed:
 *
 * Emitting first value
 * item=1
 * */
suspend fun main() {
    val flow = flow {
        delay(100)

        println("Emitting first value")
        emit(1)

        delay(100)

        println("Emitting second value")
        emit(2)
    }

    /**
     * When first item has received the flow became ended and second value is ignored.
     * */
    val item = flow.first()
    println("item=$item")
}