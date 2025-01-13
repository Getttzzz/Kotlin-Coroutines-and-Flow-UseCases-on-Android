package com.lukaslechner.coroutineusecasesonandroid.playground.flow.basics

import com.lukaslechner.coroutineusecasesonandroid.playground.utils.printWithTimePassed
import java.math.BigInteger

//30ms: result=1
//43ms: result=2
//54ms: result=6
//67ms: result=24
//79ms: result=120
// Got result not all at once but each iteration.
// Now I have a data stream!
// This stream is synchronous.
// The downside of a sequence that it blocks main thread.
fun main() {
    val startTime = System.currentTimeMillis()
    calculateFactorialOf(5).forEach {
        printWithTimePassed("result=$it", startTime)
    }
}
private suspend fun blablacar(){

}

private fun calculateFactorialOf(number: Int): Sequence<BigInteger> = sequence {
    var factorial = BigInteger.ONE
    for (i in 1..number) {
        Thread.sleep(10)
        factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        yield(factorial)
    }
}