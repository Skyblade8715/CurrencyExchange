package com.skycom.currencyexchange.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRateDto(
    @SerialName("currency")
    val currency: String,
    @SerialName("code")
    val code: String,
    @SerialName("mid")
    val mid: Double,
)