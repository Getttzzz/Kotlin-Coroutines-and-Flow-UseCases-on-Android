package com.lukaslechner.coroutineusecasesonandroid.playground.flow.builders

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

/**
* I can use "suspend" main function. It would worked the same if I had to add "runBlocking{}".
 *
 * Basic Flow Builders:
 * 1) flowOf(1,2,3) - create a flow from a fixed set of values.
 * 2) .asFlow() - extension fun on various types to convert them into Flow.
 * 3) flow{} - builder function to construct arbitrary flows from sequential call to the emit function.
 *          flow {
 *              emit("one")
 *              delay(100)
 *              emit("two")
 *          }
* */
suspend fun main () {

    //"collect" is a terminal operator.
    val firstFlow = flowOf<Int>(1).collect {emmitedValue ->
        println("firstFlow=$emmitedValue")
    }

    val secondFlow = flowOf(1,2,3).collect {emmitedValue ->
        println("secondFlow=$emmitedValue")
    }

    /**
     * I could transform list to the flow by using "asFlow" extension function.
     * */
    listOf("A", "B", "C").asFlow().collect {emmitedValue ->
        println("secondFlow=$emmitedValue")
    }

    flow {
        delay(2000)
        emit("item emmited after 2000ms")
    }.collect { emmitedValue ->
        println("flow{}=$emmitedValue")
    }
}