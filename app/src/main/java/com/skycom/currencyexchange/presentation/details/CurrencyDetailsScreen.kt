package com.skycom.currencyexchange.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.skycom.currencyexchange.R
import com.skycom.currencyexchange.presentation.common.toDisplayName

@Composable
fun CurrencyDetailsScreen(
    code: String,
    table: String,
    uiState: CurrencyDetailsUiState,
    onLoad: () -> Unit,
    onRetry: () -> Unit,
) {

    LaunchedEffect(code, table) {
        onLoad()
    }

    Scaffold { padding ->
        when (uiState) {
            CurrencyDetailsUiState.Loading -> {
                DetailsLoadingContent(modifier = Modifier.padding(padding))
            }

            is CurrencyDetailsUiState.Error -> {
                DetailsErrorContent(
                    modifier = Modifier.padding(padding),
                    message = uiState.message,
                    onRetry = onRetry,
                )
            }

            is CurrencyDetailsUiState.Success -> {
                DetailsContent(
                    modifier = Modifier.padding(padding),
                    details = uiState.details,
                )
            }
        }
    }
}

@Composable
private fun DetailsLoadingContent(
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
private fun DetailsErrorContent(
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
private fun DetailsContent(
    details: CurrencyDetailsDisplay,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = details.name.toDisplayName(),
                    style = MaterialTheme.typography.headlineSmall,
                )

                AssistChip(
                    onClick = {},
                    enabled = false,
                    label = {
                        Text(text = details.code)
                    },
                )

                Text(
                    text = stringResource(R.string.current_average_rate),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Text(
                    text = details.currentRate,
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }

        Text(
            text = stringResource(R.string.last_two_weeks),
            style = MaterialTheme.typography.titleMedium,
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = details.history,
                key = { it.date },
            ) { item ->
                HistoryRow(
                    item = item,
                )
            }
        }
    }
}

@Composable
private fun HistoryRow(
    item: CurrencyHistoryUiItem,
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "Average rate",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Text(
                text = item.rate,
                style = MaterialTheme.typography.titleMedium,
                color = if (item.isSignificantChange) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
            )
        }
    }
}