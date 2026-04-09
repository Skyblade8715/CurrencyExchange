package com.skycom.currencyexchange.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skycom.currencyexchange.domain.model.CurrencyListItem
import com.skycom.currencyexchange.domain.repository.CurrencyRepository
import com.skycom.currencyexchange.presentation.common.formatRate
import com.skycom.currencyexchange.presentation.common.toDisplayName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val repository: CurrencyRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CurrencyListUiState>(CurrencyListUiState.Loading)
    val uiState: StateFlow<CurrencyListUiState> = _uiState.asStateFlow()

    init {
        loadCurrencies()
    }

    fun loadCurrencies() {
        viewModelScope.launch {
            _uiState.value = CurrencyListUiState.Loading

            runCatching {
                repository.getCurrencies()
            }.onSuccess { domainItems ->
                _uiState.value = CurrencyListUiState.Success(
                    data = domainItems.map { it.toUiItem() },
                )
            }.onFailure {
                _uiState.value = CurrencyListUiState.Error(
                    message = it.message,
                )
            }
        }
    }

    private fun CurrencyListItem.toUiItem() = CurrencyListUiItem(
        name = currencyName.toDisplayName(),
        code = code,
        displayRate = formatRate(currentRate),
        table = table.value,
    )
}