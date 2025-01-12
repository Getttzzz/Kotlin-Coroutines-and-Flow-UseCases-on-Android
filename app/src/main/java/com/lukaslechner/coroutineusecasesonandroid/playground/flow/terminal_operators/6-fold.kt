package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
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

    // 5 + 1 = 6 //so it will return 6 to the next iteration because we have two emission.
    // 6 + 2 = 8 //will be returned and terminated because there's no emission anymore.
    val item = flow.fold(5) { acc: Int, emitted: Int ->
        acc + emitted
    }

    println("item=$item")
}