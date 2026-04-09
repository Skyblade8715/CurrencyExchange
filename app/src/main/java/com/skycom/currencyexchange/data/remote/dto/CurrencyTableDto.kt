package com.skycom.currencyexchange.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyTableDto(
    @SerialName("table")
    val table: String,
    @SerialName("no")
    val no: String,
    @SerialName("effectiveDate")
    val effectiveDate: String,
    @SerialName("rates")
    val rates: List<CurrencyRateDto>,
)