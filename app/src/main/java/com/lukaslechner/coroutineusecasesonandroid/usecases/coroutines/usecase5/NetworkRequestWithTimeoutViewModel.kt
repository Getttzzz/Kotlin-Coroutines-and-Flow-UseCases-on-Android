package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

class NetworkRequestWithTimeoutViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest(timeout: Long) {
        uiState.value = UiState.Loading

        //usingWithTimeout(timeout)

        usingWithTimeoutOrNull(timeout)
    }

    private fun usingWithTimeout(timeout: Long) {
        viewModelScope.launch {
            try {
                //When time is over it throws TimeoutCancellationException.
                val recentAndroidVersions = withTimeout(timeout) {
                    api.getRecentAndroidVersions()
                }
                uiState.value = UiState.Success(recentAndroidVersions)
            } catch (timeoutException: TimeoutCancellationException) {
                uiState.value = UiState.Error("Timeout exception")
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Failed")
            }
        }
    }

    private fun usingWithTimeoutOrNull(timeout: Long) {
        viewModelScope.launch {
            try {
                //When time is over it throws TimeoutCancellationException.
                val recentAndroidVersions = withTimeoutOrNull(timeout) {
                    api.getRecentAndroidVersions()
                }
                if (recentAndroidVersions != null) {
                    uiState.value = UiState.Success(recentAndroidVersions)
                }else{
                    uiState.value = UiState.Error("Timeout (got null)")
                }
            } catch (timeoutException: TimeoutCancellationException) {
                uiState.value = UiState.Error("Timeout exception")
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Failed")
            }
        }
    }

}