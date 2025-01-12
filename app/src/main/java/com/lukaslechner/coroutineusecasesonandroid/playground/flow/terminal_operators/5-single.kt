package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

suspend fun main() {
    val flow = flow {
        delay(100)

        println("Emitting first value")
        emit(1)

        delay(100)

        println("Emitting second value")
        emit(2)
    }

    //Exception in thread "main" java.lang.IllegalArgumentException: Flow has more than one element
    //SO I need to use only one emit in flow or use "singleOrNull" terminal operator.
    val item = flow.single()

    println("item=$item")
}