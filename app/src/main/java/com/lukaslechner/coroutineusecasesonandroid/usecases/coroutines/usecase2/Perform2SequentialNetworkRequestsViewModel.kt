package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import kotlinx.coroutines.launch
import timber.log.Timber

class Perform2SequentialNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val recentAndroidVersions: List<AndroidVersion> =
                    mockApi.getRecentAndroidVersions()

                val androidVersionFeatures: VersionFeatures =
                    mockApi.getAndroidVersionFeatures(recentAndroidVersions.last().apiLevel)

                uiState.value = UiState.Success(androidVersionFeatures)
            } catch (e: Exception) {
                Timber.e(e)
                uiState.value = UiState.Error(e.message.orEmpty())
            }
        }
    }
}