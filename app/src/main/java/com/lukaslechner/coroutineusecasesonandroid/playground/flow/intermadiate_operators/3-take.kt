package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermadiate_operators

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile

suspend fun main() {
    flowOf(1, 2, 3, 4, 5)
        .take(3) //take only first three emissions and than cancels the flow
        .collect { collectedValue -> println("value=$collectedValue") }
    println("===")
    flowOf(1, 2, 3, 4, 5)
        .takeWhile { it <= 3 }
        .collect { collectedValue -> println("value=$collectedValue") }

    /**
     * Main question here what's the difference between "takeWhile" and "filter"?
     * "filter" operator doesn't cancel the upstream flow.
     * "takeWhile" operator cancels the upstream flow.
     * */
}