package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow

suspend fun main():Unit = coroutineScope {

    // "emit" will call "collect" method under the hood.
    // That's why this example won't get a runtime crash.
    // This example demonstrates violation of the Exception Transparency rule.
    // Don't use "try-catch" construnction inside upstream flow.
    // Use "catch" operator to catch the upstream flow exception.
    flow {
       try {
           emit(1)
       } catch (e: Exception) {
           println("Catch exception in the flow builder")
       }
    }.collect {
        throw Exception("exception in collect")
    }

}