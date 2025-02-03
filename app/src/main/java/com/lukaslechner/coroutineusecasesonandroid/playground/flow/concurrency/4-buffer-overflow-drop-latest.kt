package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow

suspend fun main() = coroutineScope {

    /**
     * dropping last baked pancakes, first pancake
     * that is trapped into the buffer will be processed soon.
     * */
    val flow = flow {
        repeat(5){
            println("Emitter: Start cooking pancake $it")
            delay(100)
            println("Emitter: Pancake $it ready!")
            emit(it)
        }
    }.buffer(capacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    flow.collect{
        println("Collector: Start eating pancake $it")
        delay(300)
        println("Collector: Finished eating pancake $it")
    }

}