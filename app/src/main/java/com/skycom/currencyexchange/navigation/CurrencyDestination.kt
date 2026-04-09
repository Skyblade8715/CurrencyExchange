package com.skycom.currencyexchange.navigation

sealed class CurrencyDestination(val route: String) {
    data object List : CurrencyDestination("currency_list")
    data object Details : CurrencyDestination("currency_details/{code}/{table}") {
        fun createRoute(code: String, table: String): String {
            return "currency_details/$code/$table"
        }
    }
}