package com.lukaslechner.coroutineusecasesonandroid.playground.flow.channel

import kotlinx.coroutines.async
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * */
suspend fun main(): Unit = coroutineScope {
    val channel = produce<Int> {
        println("sending 10")
        send(10)

        println("sending 20")
        send(20)
    }

    /**
     * Differences between hot flows and channels:
     * With hot flows the emission are shared between all collectors.
     * If I would use a hot flow here, then both collectors would receive the values 10 AND 20.
     * But since I'm using a channel, each value is only consumed by a single subscriber.
     * Channels are kind of "fair".
     * They evenly distribute elements among consumers.
     *
     *
     * Channels
     * - low-level inter-coroutine communication primitive
     * - flows and some flow operators are built on top of channels
     * - events are consumed exactly once by a single subscriber
     * - From article: a channel doesn't guarantee the delivery and processing of the events.
     * */
    launch {
        channel.consumeEach { receivedValue ->
            println("Consumer 1: receivedValue=$receivedValue")
        }
    }

    launch {
        channel.consumeEach { receivedValue ->
            println("Consumer 2: receivedValue=$receivedValue")
        }
    }
}