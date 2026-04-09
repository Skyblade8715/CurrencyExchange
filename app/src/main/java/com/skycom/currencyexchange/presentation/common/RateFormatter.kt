package com.skycom.currencyexchange.presentation.common

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

private val symbols = DecimalFormatSymbols(Locale.US).apply {
    decimalSeparator = '.'
}

private val rateFormatter = DecimalFormat("0.0000####", symbols)

fun formatRate(value: Double): String {
    return rateFormatter.format(value)
}