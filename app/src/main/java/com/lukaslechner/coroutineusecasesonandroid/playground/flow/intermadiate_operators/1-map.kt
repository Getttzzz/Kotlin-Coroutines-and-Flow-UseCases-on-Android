package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermadiate_operators

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

suspend fun main() {
    flowOf(1, 2, 3, 4, 5)
        // I can modify the data in map operator AND
        // I can even change return type of the value
        // So I can return String instead of an Integer
        // There's a site https://flowmarbles.com/#map that illustrates work of different operators.
        .map {
            "Emission=$it"
        }
        .collect { collectedValue -> println("value=$collectedValue") }
}