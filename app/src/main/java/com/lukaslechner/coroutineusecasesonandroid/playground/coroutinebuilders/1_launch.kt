package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//GETZ.<top>.networkRequest--> start
//GETZ.<top>.networkRequest--> end
//GETZ.<top>.main--> end of main
fun main() = runBlocking {
    val job = launch {
        networkRequest()
    }
    job.join() // suspend current coroutine in order to suspend root coroutine to finish network request
    println("GETZ.<top>.main--> end of main")
}

private suspend fun networkRequest():String{
    println("GETZ.<top>.networkRequest--> start")
    delay(500)
    println("GETZ.<top>.networkRequest--> end")
    return "Result"
}
