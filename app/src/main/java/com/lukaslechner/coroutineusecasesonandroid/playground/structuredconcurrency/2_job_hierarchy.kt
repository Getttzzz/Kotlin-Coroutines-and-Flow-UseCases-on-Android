package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val scopeJob = Job()
    val scope = CoroutineScope(
        Dispatchers.Default +
                scopeJob +
                CoroutineName("Yurii's coroutine")
    )

    val coroutineJob = scope.launch {
        println("GETZ.<top>.main--> Start coroutine")
        delay(1000)
    }

    println("GETZ.<top>.main--> is coroutineJob a child of scopeJob? => ${scopeJob.children.contains(coroutineJob)}")

}