package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase12

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.math.BigInteger

class FactorialCalculator(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun calculateFactorial(
        factorialOf: Int,
        numberOfCoroutines: Int,
        scope: CoroutineScope,
    ): BigInteger {

        /**
         * if [numberOfCoroutines] is 3 then it will be 3 subRanges.
         * */
        val subRanges: List<SubRange> =
            scope.async(defaultDispatcher) {
                createSubRangeList(factorialOf, numberOfCoroutines)
            }.await()


        var tempResult = BigInteger.ONE
        subRanges.forEach { range ->
            val resultOfSubRange =
                scope.async(defaultDispatcher) { calculateFactorialOfSubRange(range) }.await()
            val bigInteger: BigInteger = tempResult.multiply(resultOfSubRange)
            tempResult = bigInteger

        }

        return tempResult
    }

    // TODO: execute on background thread
    fun calculateFactorialOfSubRange(
        subRange: SubRange
    ): BigInteger {
        var factorial = BigInteger.ONE
        for (i in subRange.start..subRange.end) {
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        }
        return factorial
    }

    fun createSubRangeList(
        factorialOf: Int,
        numberOfSubRanges: Int
    ): List<SubRange> {
        val quotient = factorialOf.div(numberOfSubRanges)
        val rangesList = mutableListOf<SubRange>()

        var curStartIndex = 1
        repeat(numberOfSubRanges - 1) {
            rangesList.add(
                SubRange(
                    curStartIndex,
                    curStartIndex + (quotient - 1)
                )
            )
            curStartIndex += quotient
        }
        rangesList.add(SubRange(curStartIndex, factorialOf))
        return rangesList
    }
}


data class SubRange(val start: Int, val end: Int)