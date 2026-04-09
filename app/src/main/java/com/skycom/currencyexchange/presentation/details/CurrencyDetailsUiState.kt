package com.skycom.currencyexchange.presentation.details


sealed interface CurrencyDetailsUiState {
    data object Loading : CurrencyDetailsUiState

    data class Success(
        val details: CurrencyDetailsDisplay,
    ) : CurrencyDetailsUiState

    data class Error(
        val message: String?,
    ) : CurrencyDetailsUiState
}