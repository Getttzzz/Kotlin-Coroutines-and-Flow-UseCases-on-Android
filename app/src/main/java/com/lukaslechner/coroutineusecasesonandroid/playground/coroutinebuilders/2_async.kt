package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {

    val startTime = System.currentTimeMillis()

    //This code executes two network requests in parallel.
    //However there's a problem. [resultList] is a shared mutable state for a result.
    //It leads to potential modification of this shared resource from a different threads.
    //Yeah, I can change dispatcher of, for example, second coroutine and we
    //are getting into a trouble of modification shared resource.

    //A general rule in concurrent programming is to avoid shared mutable state whenever possible!
    val resultList = mutableListOf<String>()

    val job1 = launch {
        val result1 = networkCall(1)
        resultList.add(result1)
        println("GETZ.<top>.main--> result1 received $result1 after ${elapsedMillis(startTime)}")
    }
    val job2 = launch {
        val result2 = networkCall(2)
        resultList.add(result2)
        println("GETZ.<top>.main--> result2 received $result2 after ${elapsedMillis(startTime)}")
    }

    job1.join()
    job2.join()

    println("GETZ.<top>.main--> Result list: $resultList after ${elapsedMillis(startTime)}")
}

private suspend fun networkCall(number: Int): String {
    delay(1000)
    return "Result $number"
}

private fun elapsedMillis(startTime:Long) = System.currentTimeMillis() - startTime