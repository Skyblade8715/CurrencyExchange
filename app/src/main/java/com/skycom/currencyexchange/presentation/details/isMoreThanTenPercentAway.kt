package com.skycom.currencyexchange.presentation.details

import kotlin.math.abs

fun isMoreThanTenPercentAway(
    value: Double,
    currentValue: Double,
): Boolean {
    if (currentValue == 0.0) return false
    return abs(value - currentValue) / currentValue > 0.10
}