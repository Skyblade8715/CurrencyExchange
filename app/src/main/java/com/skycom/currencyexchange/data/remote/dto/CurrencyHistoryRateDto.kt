package com.skycom.currencyexchange.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyHistoryRateDto(
    @SerialName("effectiveDate")
    val effectiveDate: String,
    @SerialName("mid")
    val mid: Double,
)