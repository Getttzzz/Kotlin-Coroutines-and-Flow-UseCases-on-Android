package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase11

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class CooperativeCancellationViewModel(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    fun uiState(): LiveData<UiState> = uiState

    private val uiState: MutableLiveData<UiState> = MutableLiveData()

    private var calculationJob: Job? = null

    fun performCalculation(factorialOf: Int) {
        uiState.value = UiState.Loading
        calculationJob = viewModelScope.launch {
            var result: BigInteger = BigInteger.ONE

            val computationDuration = measureTimeMillis {
                result = calculateFactorialOf(factorialOf)
            }

            var resultString = ""
            val stringConversionDuration = measureTimeMillis {
                resultString = withContext(
                    Dispatchers.Default + CoroutineName("String Conversion Coroutine")
                ) { result.toString() }
            }

            uiState.value = UiState.Success(resultString, computationDuration, stringConversionDuration)
        }

        calculationJob?.invokeOnCompletion { throwable ->
            if (throwable is CancellationException) {
                println("GETZ.CooperativeCancellationViewModel.performCalculation--> was cancelled.")
            }
        }
    }

    fun cancelCalculation() {
        calculationJob?.cancel()
    }

    private suspend fun calculateFactorialOf(number: Int): BigInteger {
        return withContext(Dispatchers.Default) {
            var factorial = BigInteger.ONE
            for (i in 1..number) {
                yield()
                factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
            }
            println("GETZ.CooperativeCancellationViewModel.calculateFactorialOf--> Calc completed!")
            factorial
        }
    }
}