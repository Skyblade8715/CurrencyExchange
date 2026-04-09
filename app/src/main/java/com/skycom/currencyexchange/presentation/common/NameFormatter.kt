package com.skycom.currencyexchange.presentation.common

fun String.toDisplayName(): String {
    return replaceFirstChar { it.uppercase() }
}