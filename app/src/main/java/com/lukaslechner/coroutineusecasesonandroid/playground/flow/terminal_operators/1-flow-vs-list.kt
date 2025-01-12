package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

/**
 * Print:
 * add 1 to list
 * add 2 to list
 *
 *
 * Because flow was just created but didn't collected anywhere.
 * There should be a terminal operator (like "collect()") in order to consume flow items.
 *
 * */
fun main() {
    val flow = flow {
        delay(100)

        println("Emitting first value")
        emit(1)

        delay(100)

        println("Emitting second value")
        emit(2)
    }

    val list = buildList {
        add(1)
        println("add 1 to list")

        add(2)
        println("add 2 to list")
    }
}