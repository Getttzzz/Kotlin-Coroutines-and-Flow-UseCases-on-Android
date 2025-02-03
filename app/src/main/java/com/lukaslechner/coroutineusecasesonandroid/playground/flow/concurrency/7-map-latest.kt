package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest

suspend fun main() = coroutineScope {

    /**
     * */
    val flow = flow {
        repeat(5) {
            println("Emitter: Start cooking pancake $it")
            delay(100)
            println("Emitter: Pancake $it ready!")
            emit(it)
        }
    }
        /**
         * "mapLatest" is useful in situations where we have a slow operation
         * in its block and we again don't care about outdated emissions.
         *
         * Docs: when the original flow emits a new value,
         * the computation of the "block" for the previous value is cancelled.
         * */
        .mapLatest {
            println("Add topping onto the pancake $it")
            delay(200)
            it
        }


    flow.collect {
        println("Collector: Start eating pancake $it")
        delay(300)
        println("Collector: Finished eating pancake $it")
    }

}