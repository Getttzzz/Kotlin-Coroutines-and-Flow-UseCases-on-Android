package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase6

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.launch
import timber.log.Timber

class RetryNetworkRequestViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            val numberOfRetries = 2
            try {
                myRetry(numberOfRetries) {
                    loadRequest()
                }
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network Request Has Failed")
            }
        }

    }

    private suspend fun <T> myRetry(numberOfRetries: Int, block: suspend () -> T): T {
        repeat(numberOfRetries) {
            try {
                return block()
            } catch (exception: Exception) {
                Timber.e(exception)
            }
        }
        return block()
    }

    private suspend fun loadRequest() {
        val recentVersions = api.getRecentAndroidVersions()
        uiState.value = UiState.Success(recentVersions)
    }

}