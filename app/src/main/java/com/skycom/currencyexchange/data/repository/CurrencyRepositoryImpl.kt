package com.skycom.currencyexchange.data.repository

import com.skycom.currencyexchange.data.mapper.toCurrencyDetails
import com.skycom.currencyexchange.data.mapper.toCurrencyListItem
import com.skycom.currencyexchange.data.remote.api.NbpApiService
import com.skycom.currencyexchange.domain.model.CurrencyDetails
import com.skycom.currencyexchange.domain.model.CurrencyListItem
import com.skycom.currencyexchange.domain.model.CurrencyTable
import com.skycom.currencyexchange.domain.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: NbpApiService,
) : CurrencyRepository {

    override suspend fun getCurrencies(): List<CurrencyListItem> = withContext(Dispatchers.IO) {
        coroutineScope {
            val tableADeferred = async { api.getTable(CurrencyTable.A.value) }
            val tableBDeferred = async { api.getTable(CurrencyTable.B.value) }

            val ratesA =
                runCatching { tableADeferred.await() }.getOrNull()?.firstOrNull()?.rates.orEmpty()
                    .map { it.toCurrencyListItem(table = CurrencyTable.A) }

            val ratesB =
                runCatching { tableBDeferred.await() }.getOrNull()?.firstOrNull()?.rates.orEmpty()
                    .map { it.toCurrencyListItem(table = CurrencyTable.B) }

            (ratesA + ratesB).sortedBy { it.currencyName.lowercase() }
        }
    }

    override suspend fun getCurrencyDetails(
        code: String,
        table: String,
        startDate: LocalDate,
        endDate: LocalDate,
    ): CurrencyDetails = withContext(Dispatchers.IO) {
        coroutineScope {
            val currentDeferred = async { api.getCurrentCurrencyRate(table, code) }
            val historyDeferred = async {
                api.getCurrencyHistory(table, code, startDate.toString(), endDate.toString())
            }

            val currentRate = currentDeferred.await().rates.firstOrNull()?.mid ?: 0.0
            historyDeferred.await().toCurrencyDetails(currentRate)
        }
    }
}