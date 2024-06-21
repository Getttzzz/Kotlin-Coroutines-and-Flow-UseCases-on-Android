package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase12

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class CalculationInSeveralCoroutinesViewModel(
    private val factorialCalculator: FactorialCalculator = FactorialCalculator(),
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : BaseViewModel<UiState>() {

    fun performCalculation(
        factorialOf: Int,
        numberOfCoroutines: Int
    ) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            var factorialResult = BigInteger.ZERO
            val computationDuration = measureTimeMillis {
                factorialResult =
                    factorialCalculator.calculateFactorial(factorialOf, numberOfCoroutines, this)
            }

            var resultString = ""
            val stringConversionDuration = measureTimeMillis {
                resultString = viewModelScope.async {
                    convertToString(factorialResult, defaultDispatcher)
                }.await()
            }

            uiState.value =
                UiState.Success(resultString, computationDuration, stringConversionDuration)
        }
    }

    private suspend fun convertToString(
        number: BigInteger,
        defaultDispatcher: CoroutineDispatcher
    ) = withContext(defaultDispatcher) {
        number.toString()
    }
}