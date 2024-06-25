package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

fun main() {
    val scope = CoroutineScope(Job())

    scope.launch {
        val supervisorJob = supervisorScope {
            launch {
                println("GETZ.<top>.main--> 1 start")
                delay(100)
                println("GETZ.<top>.main--> 1 end")
            }
            launch {
                println("GETZ.<top>.main--> 2 start")
                delay(200)
//                throw RuntimeException()
                println("GETZ.<top>.main--> 2 end")
            }
        }

        println("GETZ.<top>.main--> supervisorJob.isActive=${supervisorJob.isActive}")
        launch {
            println("GETZ.<top>.main--> 3 start")
            delay(300)
            println("GETZ.<top>.main--> 3 end")
        }
    }

    // How to run cor1 and cor2 together in parallel and then run cor3?
    // Answer is to use scoping function. It is ideomatic way to combine cor1 and cor2
    // without using "join()".

    // Another topic is "supervisorJob".

    // What's the difference between coroutineScope and supervisorScope?
    // "coroutineScope": when cor2 or cor1 got failed the exception will be propagated up to
    // a parent job ("scope" in this example) and parent job will be cancelled.
    // "supervisorScope": when cor2 got failed the exception will be propagated up to current scope
    // I mean to the supervisorJob. However, cor3 will finish its task.

    Thread.sleep(1000)
}