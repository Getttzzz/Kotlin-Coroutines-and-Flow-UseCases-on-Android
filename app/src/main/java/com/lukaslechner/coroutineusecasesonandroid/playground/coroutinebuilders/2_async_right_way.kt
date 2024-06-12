package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {

    val startTime = System.currentTimeMillis()

    val deferred1: Deferred<String> = async {
        val result1 = networkCall(1)
        println("GETZ.<top>.main--> result1 received $result1 after ${elapsedMillis(startTime)}")
        result1
    }
    val deferred2: Deferred<String> = async {
        val result2 = networkCall(2)
        println("GETZ.<top>.main--> result2 received $result2 after ${elapsedMillis(startTime)}")
        result2
    }

    val str1 = deferred1.await()
    val str2 = deferred2.await()

    val resultList = listOf(str1, str2)
    println("GETZ.<top>.main--> Result list: $resultList after ${elapsedMillis(startTime)}")
}

private suspend fun networkCall(number: Int): String {
    delay(1000)
    return "Result $number"
}

private fun elapsedMillis(startTime:Long) = System.currentTimeMillis() - startTime