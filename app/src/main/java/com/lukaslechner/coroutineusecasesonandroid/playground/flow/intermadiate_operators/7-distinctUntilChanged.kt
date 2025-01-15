package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermadiate_operators

import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf

suspend fun main() {
    flowOf(1, 1, 1, 2, 3, 4, 5)
        .distinctUntilChanged()
        .collect { println("$it") }

    /** Filtered out all the repetitions.
     * 1
     * 2
     * 3
     * 4
     * 5
     * */
}