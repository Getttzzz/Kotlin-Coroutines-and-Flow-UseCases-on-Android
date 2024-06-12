package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase4

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class VariableAmountOfNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {

    }

    fun performNetworkRequestsConcurrently() {
        uiState.value = UiState.Loading

        //my solution
        viewModelScope.launch {
            try {
                val recentVersions = mockApi.getRecentAndroidVersions()
                val listOfDeferred = mutableListOf<Deferred<VersionFeatures>>()
                for (version in recentVersions) {
                    val deferred: Deferred<VersionFeatures> =
                        async { mockApi.getAndroidVersionFeatures(version.apiLevel) }
                    listOfDeferred.add(deferred)
                }
                val result: List<VersionFeatures> = listOfDeferred.awaitAll()

                uiState.value = UiState.Success(result)
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network Request Failed")
            }
        }

        // Lukas's solution + my modifications
        viewModelScope.launch {
            try {
                mockApi.getRecentAndroidVersions()
                    .map { androidVersion ->
                        async { mockApi.getAndroidVersionFeatures(androidVersion.apiLevel) }
                    }
                    .awaitAll()
                    .let { versions ->
                        uiState.value = UiState.Success(versions)
                    }
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network Request Failed")
            }
        }
    }


}