package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import timber.log.Timber

class PerformNetworkRequestsConcurrentlyViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val oreoFeatures = mockApi.getAndroidVersionFeatures(27)
                val pieFeatures = mockApi.getAndroidVersionFeatures(28)
                val android10Features = mockApi.getAndroidVersionFeatures(29)

                val versionFeatures = listOf(oreoFeatures, pieFeatures, android10Features)
                uiState.value = UiState.Success(versionFeatures)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun performNetworkRequestsConcurrently() {
        //async coroutine builder helps here to start a few network requests simultaneously.
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val oreoDeferred = async { mockApi.getAndroidVersionFeatures(27) }
                val pieDeferred = async { mockApi.getAndroidVersionFeatures(28) }
                val android10Deferred = async { mockApi.getAndroidVersionFeatures(29) }

                val features = listOf(
                    oreoDeferred.await(),
                    pieDeferred.await(),
                    android10Deferred.await()
                )
                //or
                val features2 = awaitAll(oreoDeferred, pieDeferred, android10Deferred)

                uiState.value = UiState.Success(features2)
            } catch (e: Exception) {
                Timber.e(e)
                uiState.value = UiState.Error("Network Requests Failed.")
            }
        }
    }
}