package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermadiate_operators

import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile

suspend fun main() {
    flowOf(1, 2, 3, 4, 5)
        .drop(3)
        .collect { collectedValue -> println("value=$collectedValue") }

    /**
     * Usually, "drop" operator is used like "drop(1)".
     * That way it will skip first emission. It could be some loading state or any other
     * value that doesn't need to use for now.
     * */
}