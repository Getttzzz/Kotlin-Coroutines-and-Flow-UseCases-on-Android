package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermadiate_operators

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

suspend fun main() {
    flowOf(1, 2, 3, 4, 5)
        .filter { it > 3} // or filterNot or filterIsInstance<>
        .collect { collectedValue -> println("value=$collectedValue") }
}