package com.skycom.currencyexchange.presentation.list

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.skycom.currencyexchange.FakeCurrencyRepository
import com.skycom.currencyexchange.domain.model.CurrencyListItem
import com.skycom.currencyexchange.domain.model.CurrencyTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyListViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var repository: FakeCurrencyRepository
    private lateinit var viewModel: CurrencyListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        repository = FakeCurrencyRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `emits Loading then Success when repository returns data`() = runTest {
        repository.currencies = listOf(
            CurrencyListItem(
                currencyName = "Dollar",
                code = "USD",
                currentRate = 4.0,
                table = CurrencyTable.A,
            )
        )

        viewModel = CurrencyListViewModel(repository)

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(CurrencyListUiState.Loading)

            dispatcher.scheduler.advanceUntilIdle()

            val success = awaitItem()
            assertThat(success).isInstanceOf(CurrencyListUiState.Success::class.java)

            val data = (success as CurrencyListUiState.Success).data
            assertThat(data).hasSize(1)
            assertThat(data.first().code).isEqualTo("USD")
        }
    }

    @Test
    fun `emits Loading then Error when repository throws`() = runTest {
        repository.shouldThrow = true

        viewModel = CurrencyListViewModel(repository)

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(CurrencyListUiState.Loading)

            dispatcher.scheduler.advanceUntilIdle()

            val error = awaitItem()
            assertThat(error).isInstanceOf(CurrencyListUiState.Error::class.java)
        }
    }
}