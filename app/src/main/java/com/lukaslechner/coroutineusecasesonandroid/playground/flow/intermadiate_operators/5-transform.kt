package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermadiate_operators

import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.transform

suspend fun main() {
    flowOf(1, 2, 3, 4, 5)
        .transform {
            emit(it)
            emit(it * 10)
        }
        .collect { collectedValue -> println("value=$collectedValue") }

    /**
     * For "transform" operator I have "FlowCollector" as a receiver.
     * That means that I can call "emit" inside transform. For example,
     * I can emit twice (original value and new value).
     *
     * Additionally, "transform" could be used as a building block for new operator.
     * ```
     * fun Flow<Int>.skipOddAndDuplicateEven(): Flow<Int> = transform { value ->
     *     if (value % 2 == 0) { // Emit only even values, but twice
     *         emit(value)
     *         emit(value)
     *     } // Do nothing if odd
     * }
     * ```
     * */
}