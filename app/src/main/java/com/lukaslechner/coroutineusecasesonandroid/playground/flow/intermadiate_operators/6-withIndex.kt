package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermadiate_operators

import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.withIndex

suspend fun main() {
    flowOf(1, 2, 3, 4, 5)
        .withIndex()
        .collect { println("$it") }

    /**
     * Will be printed:
     * IndexedValue(index=0, value=1)
     * IndexedValue(index=1, value=2)
     * IndexedValue(index=2, value=3)
     * IndexedValue(index=3, value=4)
     * IndexedValue(index=4, value=5)
     * */
}