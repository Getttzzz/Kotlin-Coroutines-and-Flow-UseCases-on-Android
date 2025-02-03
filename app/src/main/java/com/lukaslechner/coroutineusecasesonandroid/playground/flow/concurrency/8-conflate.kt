package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow

suspend fun main() = coroutineScope {

    /**
     * */
    val flow = flow {
        repeat(5){
            println("Emitter: Start cooking pancake $it")
            delay(100)
            println("Emitter: Pancake $it ready!")
            emit(it)
        }
    }
        /**
         * Conflate - means combine or group.
         *
         * Shortcut for "buffer(capacity=0,onBufferOverflow=DROP_OLDEST)
         * which means to keep only one the freshest value.
         *
         * This kind of similar to StateFlow.
         * */
        .conflate()

    flow.collect{
        println("Collector: Start eating pancake $it")
        delay(300)
        println("Collector: Finished eating pancake $it")
    }

}