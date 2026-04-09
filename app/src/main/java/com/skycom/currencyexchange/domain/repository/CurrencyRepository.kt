package com.skycom.currencyexchange.domain.repository

import com.skycom.currencyexchange.domain.model.CurrencyDetails
import com.skycom.currencyexchange.domain.model.CurrencyListItem
import java.time.LocalDate

interface CurrencyRepository {
    suspend fun getCurrencies(): List<CurrencyListItem>

    suspend fun getCurrencyDetails(
        code: String,
        table: String,
        startDate: LocalDate,
        endDate: LocalDate,
    ): CurrencyDetails
}