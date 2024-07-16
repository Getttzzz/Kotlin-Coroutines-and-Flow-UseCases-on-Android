package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase13

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.*
import timber.log.Timber

class ExceptionHandlingViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun handleExceptionWithTryCatch() {

    }

    fun handleWithCoroutineExceptionHandler() {

    }

    fun showResultsEvenIfChildCoroutineFails() {
        uiState.value = UiState.Loading

        //Don't use handling of exception this way:
        //It leads to runtime crash.
//        viewModelScope.launch {
//            val oreoDeferred = async { api.getAndroidVersionFeatures(27) }
//            val pieDeferred = async { api.getAndroidVersionFeatures(28) }
//            val android10Deferred = async { api.getAndroidVersionFeatures(29) }
//
//            val oreoFeatures = try {
//                oreoDeferred.await()
//            } catch (e: Exception) {
//                println("GETZ error loading features; e=$e")
//                null
//            }
//
//            val features = listOfNotNull(oreoFeatures, pieDeferred.await(), android10Deferred.await())
//
//            uiState.value = UiState.Success(features)
//        }

        viewModelScope.launch() {

            //You gotta wrap in "supervisorScope" your children coroutines
            //in order to avoid cancelling siblings and parent itself when one of the siblings
            //got cancelled.
            supervisorScope {
                val oreoDeferred = async { api.getAndroidVersionFeatures(27) }
                val pieDeferred = async { api.getAndroidVersionFeatures(28) }
                val android10Deferred = async { api.getAndroidVersionFeatures(29) }

                val result = listOf(oreoDeferred, pieDeferred, android10Deferred)
                    .mapNotNull {
                        try {
                            it.await()
                        } catch (e:Exception) {
                            println("GETZ Error while loading features.")
                            null
                        }
                    }

                uiState.value = UiState.Success(result)
            }

        }
    }
}