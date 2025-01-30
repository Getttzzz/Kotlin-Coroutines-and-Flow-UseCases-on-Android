package com.lukaslechner.coroutineusecasesonandroid.playground.flow.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.math.BigInteger
import kotlin.coroutines.EmptyCoroutineContext

suspend fun main() {
    val scope = CoroutineScope(
        EmptyCoroutineContext
                + CoroutineName("MyCoroutine")
                + Dispatchers.Default
    )

    scope.launch {
        intFlow()
            .onCompletion { throwable ->
                if (throwable is CancellationException) {
                    println("Flow has been cancelled.")
                }
            }.collect {
                println("Collected $it")

                if (it == 2) {
                    cancel()
                }
            }
    }.join()

}

/**
 * This is example of the bad approach: coroutine has been cancelled at emit(2)
 * but factorial calculation keeps running. To fix that I need simply to add
 * "ensureActive" after each cycle of calculation in for loop.
 *
 * private fun intFlow() = flow {
 *     emit(1)
 *     emit(2)
 *
 *     calculateFactorialOf(5)
 *
 *     emit(3)
 * }
 * */
private fun intFlow() = flow {
    emit(1)
    emit(2)

    println("GETZ.<top>.intFlow ---> before calc")
    calculateFactorialOf(5)
    println("GETZ.<top>.intFlow ---> after calc")

    emit(3)
}

private suspend fun calculateFactorialOf(number:Int): BigInteger = coroutineScope {
    var factorial = BigInteger.ONE
    for(i in 1..number){
        factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))

        ensureActive() // <--- this is fix that makes calculation co-operative
                       // regarding cancellation of parent coroutine.
    }
    factorial
}