package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow

suspend fun main() = coroutineScope {

    /**
     * Without "buffer()" operator here the emitter and collector
     * will be executed sequentially.
     * Pancake start cooking -> Ready -> Eating -> Finished eating
     * No parallelism.
     * No concurrency.
     * But.
     * If I add "buffer()" operator for the emitter, because they
     * create pancakes very fast, faster then collector consumes,
     * then the concurrency starts.
     *
     * Without "buffer()" the whole flow was running in the single coroutine.
     * With "buffer()" emitter runs in coroutine 1 and
     * collector runs in coroutine 2. THE CHANNEL is used internally.
     *
     * */
    val flow = flow {
        repeat(5){
            println("Emitter: Start cooking pancake $it")
            delay(100)
            println("Emitter: Pancake $it ready!")
            emit(it)
        }
    }.buffer()

    flow.collect{
        println("Collector: Start eating pancake $it")
        delay(300)
        println("Collector: Finished eating pancake $it")
    }

}