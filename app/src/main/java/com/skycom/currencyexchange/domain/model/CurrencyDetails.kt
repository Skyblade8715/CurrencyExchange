package com.skycom.currencyexchange.domain.model

data class CurrencyDetails(
    val currencyName: String,
    val code: String,
    val currentRate: Double,
    val history: List<CurrencyHistoryItem>,
)