package com.skycom.currencyexchange.data.mapper

import com.skycom.currencyexchange.data.remote.dto.CurrencyHistoryResponseDto
import com.skycom.currencyexchange.data.remote.dto.CurrencyRateDto
import com.skycom.currencyexchange.domain.model.CurrencyDetails
import com.skycom.currencyexchange.domain.model.CurrencyHistoryItem
import com.skycom.currencyexchange.domain.model.CurrencyListItem
import com.skycom.currencyexchange.domain.model.CurrencyTable
import java.time.LocalDate

fun CurrencyRateDto.toCurrencyListItem(table: CurrencyTable): CurrencyListItem {
    return CurrencyListItem(
        currencyName = currency,
        code = code,
        currentRate = mid,
        table = table,
    )
}

fun CurrencyHistoryResponseDto.toCurrencyDetails(currentRate: Double): CurrencyDetails {
    return CurrencyDetails(
        currencyName = currency,
        code = code,
        currentRate = currentRate,
        history = rates
            .map {
                CurrencyHistoryItem(
                    date = LocalDate.parse(it.effectiveDate),
                    value = it.mid,
                )
            }
            .sortedByDescending { it.date },
    )
}