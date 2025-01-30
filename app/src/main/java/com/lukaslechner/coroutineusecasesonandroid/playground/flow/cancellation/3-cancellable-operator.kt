package com.lukaslechner.coroutineusecasesonandroid.playground.flow.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

suspend fun main() {
    val scope = CoroutineScope(
        EmptyCoroutineContext
                + CoroutineName("MyCoroutine")
                + Dispatchers.Default
    )

    scope.launch {
        //Unlike "flow" builder, the "flowOf" builder
        //doesn't have internal check for co-operative cancellation
        //I need to add it by myself.
        flowOf(1,2,3)
            .onCompletion { throwable ->
                if (throwable is CancellationException) {
                    println("Flow has been cancelled.")
                }
            }
//            .onEach {
//                println("GETZ.<top>.main ---> Receive onEach it=$it")
//                //Basically, I check each emission is the job of our lovely parent coroutine is still alive?
//                //If it's not the case, throw Cancellation and finish this flow.
//                if (!currentCoroutineContext().job.isActive){
//                    throw CancellationException()
//                }
//                // or just to use "ensureActive()" that is short version of code above.
//            }
            //and as a result of all the code above
            //there's a short version of the code above, "cancellable" operator.
            .cancellable()
            .collect {
                println("Collected $it")

                if (it == 2) {
                    cancel()
                }
            }
    }.join()

}
