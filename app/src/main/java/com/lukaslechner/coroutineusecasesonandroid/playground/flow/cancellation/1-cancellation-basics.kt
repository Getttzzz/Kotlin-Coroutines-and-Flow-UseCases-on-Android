package com.lukaslechner.coroutineusecasesonandroid.playground.flow.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
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

private fun intFlow() = flow {
    emit(1)
    emit(2)
    //here I cancel the coroutine that collects the flow.
    //Because of "emit" suspend function has "ensureActive()" check
    //inside itself, I can sleep well by knowing that
    //unnecessary job wouldn't be executed by the flow.

    //However, in case of intensive computation I need explicitly
    //check whether parent coroutine is being cancelled or not. (Using "ensureActive()").
    emit(3)
}