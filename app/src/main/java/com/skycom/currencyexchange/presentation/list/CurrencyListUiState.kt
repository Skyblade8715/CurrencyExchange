package com.skycom.currencyexchange.presentation.list


sealed interface CurrencyListUiState {
    data object Loading : CurrencyListUiState
    data class Success(val data: List<CurrencyListUiItem>) : CurrencyListUiState
    data class Error(val message: String?) : CurrencyListUiState
}