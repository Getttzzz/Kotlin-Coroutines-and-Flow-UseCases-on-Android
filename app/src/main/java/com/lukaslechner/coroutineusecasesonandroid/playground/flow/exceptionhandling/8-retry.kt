package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {

    launch {
        stocksFlow()
            .catch { throwable ->
                println("Handle exception in catch() operator $throwable")
            }
            .collect { stockData ->
                println("Collected $stockData")
            }
    }

}

private fun stocksFlow(): Flow<String> = flow {
    repeat(5) { index ->
        delay(1000)//network call

        if (index < 4) {
            emit("New Stock data")
        } else {
            throw NetworkException("Network Request Has Failed.")
        }
    }
}   // Adding retry operator on emitter side
    .retry(retries = 3) { cause ->
        println("Enter retry block with cause=$cause")
        //it is good approach to add a delay for retry strategy
        //in order to avoid heavyloading of the server immediately
        //Even better will be exponentially retry.
        delay(1000)
        cause is NetworkException
    }

// or to use "retryWhen"
// it will have not only "cause" but "attemp" variable inside lambda.
// "attempt" will be incremented each retry.

class NetworkException(msg: String) : Exception(msg)