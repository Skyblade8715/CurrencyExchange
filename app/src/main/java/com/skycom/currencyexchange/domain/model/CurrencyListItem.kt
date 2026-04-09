package com.skycom.currencyexchange.domain.model

data class CurrencyListItem(
    val currencyName: String,
    val code: String,
    val currentRate: Double,
    val table: CurrencyTable,
)