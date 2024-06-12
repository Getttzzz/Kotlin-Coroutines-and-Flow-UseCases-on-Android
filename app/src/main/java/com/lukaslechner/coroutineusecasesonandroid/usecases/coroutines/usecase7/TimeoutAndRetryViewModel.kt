package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase7

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import timber.log.Timber

class TimeoutAndRetryViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading

        val retries = 2
        val timeout = 1000L

        val oreoVersionDeferred = viewModelScope.async {
            retryWithTimeout(retries, timeout) {
                api.getAndroidVersionFeatures(27)
            }
        }

        val pieVersionDeferred = viewModelScope.async {
            retryWithTimeout(retries, timeout) {
                api.getAndroidVersionFeatures(28)
            }
        }

        viewModelScope.launch {
            try {
                val versionFeatures = listOf(
                    oreoVersionDeferred,
                    pieVersionDeferred
                )
                    .awaitAll()

                uiState.value = UiState.Success(versionFeatures)

            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network Request Has Failed")

            }
        }
    }

    private suspend fun <T> retryWithTimeout(
        retries: Int = 2,
        timeout: Long,
        block: suspend () -> T
    ) = retryWithExponentialBackOff(retries) {
        withTimeout(timeout) {
            block()
        }
    }

    private suspend fun <T> retryWithExponentialBackOff(
        retries: Int = 2,
        initialDelayMillis: Long = 100,
        maxDelayMillis: Long = 1000,
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelayMillis
        repeat(retries) {
            try {
                return block()
            } catch (exception: Exception) {
                Timber.e(exception)
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
            println("GETZ.RetryNetworkRequestViewModel.retryWithExponentialBackOff--> currentDelay=$currentDelay")
        }
        return block()
    }
}