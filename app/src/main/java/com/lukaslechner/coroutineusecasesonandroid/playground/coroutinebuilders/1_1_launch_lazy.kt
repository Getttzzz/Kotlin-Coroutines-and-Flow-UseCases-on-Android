package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//GETZ.<top>.main--> job.start()
//GETZ.<top>.main--> end of main
//GETZ.<top>.networkRequest--> start
//GETZ.<top>.networkRequest--> end
fun main() = runBlocking {
    val job = launch(start = CoroutineStart.LAZY) {
        networkRequest()
    }
    delay(1000)
    println("GETZ.<top>.main--> job.start()")
    job.start() // start coroutine lazily, not right away as it was without CoroutineStart.LAZY parameter.
    println("GETZ.<top>.main--> end of main")
}

private suspend fun networkRequest():String{
    println("GETZ.<top>.networkRequest--> start")
    delay(5000)
    println("GETZ.<top>.networkRequest--> end")
    return "Result"
}
