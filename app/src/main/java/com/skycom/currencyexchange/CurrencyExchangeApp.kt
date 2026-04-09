package com.skycom.currencyexchange

import androidx.compose.runtime.Composable
import com.skycom.currencyexchange.navigation.CurrencyNavHost
import com.skycom.currencyexchange.ui.theme.CurrencyExchangeTheme

@Composable
fun CurrencyExchangeApp() {
    CurrencyExchangeTheme {
        CurrencyNavHost()
    }
}