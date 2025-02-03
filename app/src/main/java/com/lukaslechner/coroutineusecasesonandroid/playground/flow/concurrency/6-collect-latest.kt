package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
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
     * "collectLatest{}" is useful when you have a slow collector and you only care
     * about the most recent emission, therefore, when you don't want to process
     * the outdated emissions.
     *
     * Every time the upstream emits a new item, te collect latest block is
     * immediately cancelled and restarted with the new item.
     *
     * All of this construction works concurrently.
     * That's because "buffer(0)" is running under the hood.
     * */
    flow.collectLatest {
        println("Collector: Start eating pancake $it")
        delay(300)
        println("Collector: Finished eating pancake $it")
    }

}