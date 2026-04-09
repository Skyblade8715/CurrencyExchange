package com.skycom.currencyexchange.presentation.details

data class CurrencyHistoryUiItem(
    val date: String,
    val rate: String,
    val isSignificantChange: Boolean,
)