package com.lukaslechner.coroutineusecasesonandroid.playground.flow.basics

import com.lukaslechner.coroutineusecasesonandroid.playground.utils.printWithTimePassed
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.math.BigInteger


// Use "collect" function as a "forEach" for collections.
fun main() = runBlocking {
    val startTime = System.currentTimeMillis()

    launch {
        calculateFactorialOf(5).collect {
            printWithTimePassed("result=$it", startTime)
        }
    }

    println("Print at the end.")
}

private fun calculateFactorialOf(number: Int): Flow<BigInteger> = flow {
    var factorial = BigInteger.ONE
    for (i in 1..number) {
        delay(10)
        factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        emit(factorial)
    }
}.flowOn(Dispatchers.Default + CoroutineName("MyFlow"))