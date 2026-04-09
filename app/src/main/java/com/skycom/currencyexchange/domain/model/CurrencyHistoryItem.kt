package com.skycom.currencyexchange.domain.model

import java.time.LocalDate

data class CurrencyHistoryItem(
    val date: LocalDate,
    val value: Double,
)