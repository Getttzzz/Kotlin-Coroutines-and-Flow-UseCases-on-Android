package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.launch

class PerformSingleNetworkRequestViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performSingleNetworkRequest() {
        uiState.value = UiState.Loading

        //automatically cancel coroutine when ViewModel cleared in onCleared().
        //Convention: suspend functions should not block the caller thread
        viewModelScope.launch {
            // Retrofit and Room and other popular 3rd party libraries keep this convention
            // to not block a thread (MainThread here).

            // Current coroutine is only suspended (paused) while
            // the network request is running.
            val recentAndroidVersions = mockApi.getRecentAndroidVersions()

            //Retrofit internally switch execution to background thread and then
            // return result to main thread.

            //We need to switch to a background thread in case if we want to read from a file
            //or sort a big list or perform an expensive computation.

            uiState.value = UiState.Success(recentAndroidVersions)
        }
    }
}