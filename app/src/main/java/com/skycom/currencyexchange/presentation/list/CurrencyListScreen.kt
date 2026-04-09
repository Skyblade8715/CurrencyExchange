package com.skycom.currencyexchange.presentation.list

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.skycom.currencyexchange.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyListScreen(
    uiState: CurrencyListUiState,
    onRetry: () -> Unit,
    onCurrencyClick: (CurrencyListUiItem) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(text = stringResource(R.string.top_bar_title))
                        Text(
                            text = stringResource(R.string.top_bar_description),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
            )
        },
    ) { padding ->
        when (uiState) {
            CurrencyListUiState.Loading -> {
                LoadingContent(modifier = Modifier.padding(padding))
            }

            is CurrencyListUiState.Error -> {
                ErrorContent(
                    modifier = Modifier.padding(padding),
                    message = uiState.message,
                    onRetry = onRetry,
                )
            }

            is CurrencyListUiState.Success -> {
                CurrencyListContent(
                    modifier = Modifier.padding(padding),
                    currencies = uiState.data,
                    onCurrencyClick = onCurrencyClick,
                )
            }
        }
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    message: String?,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = message ?: stringResource(R.string.unkown_error_msg))
        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = onRetry,
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
private fun CurrencyListContent(
    currencies: List<CurrencyListUiItem>,
    onCurrencyClick: (CurrencyListUiItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(
            items = currencies,
            key = { "${it.table}_${it.code}" },
        ) { currency ->
            CurrencyRow(
                currency = currency,
                onClick = { onCurrencyClick(currency) },
            )
        }
    }
}

@Composable
private fun CurrencyRow(
    currency: CurrencyListUiItem,
    onClick: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .basicMarquee(),
                    maxLines = 1,
                    text = currency.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                AssistChip(
                    onClick = {},
                    enabled = false,
                    label = {
                        Text(text = currency.code)
                    },
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = stringResource(R.string.current_average_rate),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = currency.displayRate,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }
    }
}