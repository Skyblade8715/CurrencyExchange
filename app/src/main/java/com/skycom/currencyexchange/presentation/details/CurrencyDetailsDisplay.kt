package com.skycom.currencyexchange.presentation.details

data class CurrencyDetailsDisplay(
    val name: String,
    val code: String,
    val currentRate: String,
    val history: List<CurrencyHistoryUiItem>
)