package com.skycom.currencyexchange.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skycom.currencyexchange.domain.repository.CurrencyRepository
import com.skycom.currencyexchange.presentation.common.formatRate
import com.skycom.currencyexchange.presentation.common.toDisplayName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CurrencyDetailsViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val clock: java.time.Clock,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<CurrencyDetailsUiState>(CurrencyDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadCurrencyDetails(
        code: String,
        table: String,
    ) {
        viewModelScope.launch {
            _uiState.value = CurrencyDetailsUiState.Loading

            val endDate = LocalDate.now(clock)
            val startDate = endDate.minusDays(14)

            runCatching {
                repository.getCurrencyDetails(code, table, startDate, endDate)
            }.onSuccess { details ->
                _uiState.value = CurrencyDetailsUiState.Success(
                    details = CurrencyDetailsDisplay(
                        name = details.currencyName.toDisplayName(),
                        code = details.code,
                        currentRate = formatRate(details.currentRate),
                        history = details.history.map { historyItem ->
                            CurrencyHistoryUiItem(
                                date = historyItem.date.toString(),
                                rate = formatRate(historyItem.value),
                                isSignificantChange = isMoreThanTenPercentAway(
                                    value = historyItem.value,
                                    currentValue = details.currentRate,
                                ),
                            )
                        },
                    ),
                )
            }.onFailure {
                _uiState.value = CurrencyDetailsUiState.Error(
                    message = it.message,
                )
            }
        }
    }
}