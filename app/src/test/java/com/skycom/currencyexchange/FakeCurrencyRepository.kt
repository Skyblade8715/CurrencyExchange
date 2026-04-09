package com.skycom.currencyexchange

import com.skycom.currencyexchange.domain.model.CurrencyDetails
import com.skycom.currencyexchange.domain.model.CurrencyListItem
import com.skycom.currencyexchange.domain.repository.CurrencyRepository
import java.time.LocalDate

class FakeCurrencyRepository : CurrencyRepository {

    var shouldThrow = false

    var currencies: List<CurrencyListItem> = emptyList()

    var details: CurrencyDetails? = null

    override suspend fun getCurrencies(): List<CurrencyListItem> {
        if (shouldThrow) throw RuntimeException("Test error")
        return currencies
    }

    override suspend fun getCurrencyDetails(
        code: String,
        table: String,
        startDate: LocalDate,
        endDate: LocalDate,
    ): CurrencyDetails {
        if (shouldThrow) throw RuntimeException("Test error")
        return details ?: error("Details not set")
    }
}