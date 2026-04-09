package com.skycom.currencyexchange.data.remote.api

import com.skycom.currencyexchange.data.remote.dto.CurrencyHistoryResponseDto
import com.skycom.currencyexchange.data.remote.dto.CurrencyTableDto
import retrofit2.http.GET
import retrofit2.http.Path

interface NbpApiService {

    @GET("api/exchangerates/tables/{tableName}?format=json")
    suspend fun getTable(
        @Path("tableName") tableName: String,
    ): List<CurrencyTableDto>

    @GET("api/exchangerates/rates/{table}/{code}?format=json")
    suspend fun getCurrentCurrencyRate(
        @Path("table") table: String,
        @Path("code") code: String,
    ): CurrencyHistoryResponseDto

    @GET("api/exchangerates/rates/{table}/{code}/{startDate}/{endDate}?format=json")
    suspend fun getCurrencyHistory(
        @Path("table") table: String,
        @Path("code") code: String,
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String,
    ): CurrencyHistoryResponseDto
}