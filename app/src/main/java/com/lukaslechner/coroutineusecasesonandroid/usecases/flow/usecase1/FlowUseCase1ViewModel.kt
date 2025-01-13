package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class FlowUseCase1ViewModel(
    stockPriceDataSource: StockPriceDataSource
) : BaseViewModel<UiState>() {

    val currentStockPriceAsLiveData: MutableLiveData<UiState> = MutableLiveData()

    init {
        // ==== same implementation as with "launchIn" but this is more complex.
        // ==== better to use "launchIn()"
//        viewModelScope.launch {
//            stockPriceDataSource.latestStockList.collect {
//                currentStockPriceAsLiveData.value = UiState.Success(it)
//            }
//        }

        // I can have as many "onStart" as I want.
        // I can put "onStart" or "onCompletion" or "onEach" at any place in this pipeline
        // and the logic doesn't change.

        // Also let's talk about Flow processing pipelines (Upstream, downstream).
        // If I take "onCompletion" for example, put "emit(EmptyList)" there.
        // New emission will come to the operator below (Downstream). In my case to the "onEach".
        // However, there are operators that can influence upstream.
        stockPriceDataSource
            .latestStockList
            .map { stocks -> UiState.Success(stocks) as UiState }
            .onStart { emit(UiState.Loading) }
            .onEach { uiState -> currentStockPriceAsLiveData.value = uiState }
            .launchIn(viewModelScope)
    }

}