package com.skycom.currencyexchange.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyHistoryResponseDto(
    @SerialName("table")
    val table: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("code")
    val code: String,
    @SerialName("rates")
    val rates: List<CurrencyHistoryRateDto>,
)