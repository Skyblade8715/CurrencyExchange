package com.skycom.currencyexchange.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.skycom.currencyexchange.presentation.details.CurrencyDetailsScreen
import com.skycom.currencyexchange.presentation.details.CurrencyDetailsViewModel
import com.skycom.currencyexchange.presentation.list.CurrencyListScreen
import com.skycom.currencyexchange.presentation.list.CurrencyListViewModel

@Composable
fun CurrencyNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CurrencyDestination.List.route,
    ) {
        composable(CurrencyDestination.List.route) {
            val viewModel: CurrencyListViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            CurrencyListScreen(
                uiState = uiState,
                onRetry = viewModel::loadCurrencies,
                onCurrencyClick = { currency ->
                    navController.navigate(
                        CurrencyDestination.Details.createRoute(
                            code = currency.code,
                            table = currency.table,
                        )
                    )
                },
            )
        }

        composable(
            route = CurrencyDestination.Details.route,
            arguments = listOf(
                navArgument("code") { type = NavType.StringType },
                navArgument("table") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val code = backStackEntry.arguments?.getString("code").orEmpty()
            val table = backStackEntry.arguments?.getString("table").orEmpty()
            val viewModel: CurrencyDetailsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            CurrencyDetailsScreen(
                code = code,
                table = table,
                uiState = uiState,
                onLoad = {
                    viewModel.loadCurrencyDetails(
                        code = code,
                        table = table,
                    )
                },
                onRetry = {
                    viewModel.loadCurrencyDetails(
                        code = code,
                        table = table,
                    )
                },
            )
        }
    }
}