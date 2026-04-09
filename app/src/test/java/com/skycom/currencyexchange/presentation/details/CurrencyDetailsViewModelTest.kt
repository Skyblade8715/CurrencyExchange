package com.skycom.currencyexchange.presentation.details

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.skycom.currencyexchange.FakeCurrencyRepository
import com.skycom.currencyexchange.domain.model.CurrencyDetails
import com.skycom.currencyexchange.domain.model.CurrencyHistoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyDetailsViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var repository: FakeCurrencyRepository
    private lateinit var viewModel: CurrencyDetailsViewModel

    private val fixedClock = Clock.fixed(
        Instant.parse("2026-04-01T00:00:00Z"),
        ZoneOffset.UTC
    )

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
    fun `given repository returns data when loading details then emits success with mapped values`() = runTest {
        repository.details = CurrencyDetails(
            currencyName = "dollar",
            code = "USD",
            currentRate = 10.0,
            history = listOf(
                CurrencyHistoryItem(
                    date = LocalDate.of(2026, 3, 31),
                    value = 11.5,
                ),
                CurrencyHistoryItem(
                    date = LocalDate.of(2026, 3, 30),
                    value = 10.0,
                )
            )
        )

        viewModel = CurrencyDetailsViewModel(repository, fixedClock)

        viewModel.loadCurrencyDetails("USD", "a")

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(CurrencyDetailsUiState.Loading)

            dispatcher.scheduler.advanceUntilIdle()

            val success = awaitItem()
            assertThat(success).isInstanceOf(CurrencyDetailsUiState.Success::class.java)

            val details = (success as CurrencyDetailsUiState.Success).details

            assertThat(details.name).isEqualTo("Dollar")
            assertThat(details.currentRate).isEqualTo("10.0000")
            assertThat(details.history).hasSize(2)

            val first = details.history[0]
            assertThat(first.isSignificantChange).isTrue()

            val second = details.history[1]
            assertThat(second.isSignificantChange).isFalse()
        }
    }

    @Test
    fun `given repository throws when loading details then emits error`() = runTest {
        repository.shouldThrow = true

        viewModel = CurrencyDetailsViewModel(repository, fixedClock)

        viewModel.loadCurrencyDetails("USD", "a")

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(CurrencyDetailsUiState.Loading)

            dispatcher.scheduler.advanceUntilIdle()

            val error = awaitItem()
            assertThat(error).isInstanceOf(CurrencyDetailsUiState.Error::class.java)
        }
    }
}