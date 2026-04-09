package com.skycom.currencyexchange.presentation.details

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CurrencyFormattingTest {

    @Test
    fun `given value more than 10 percent above current when comparing then returns true`() {
        val result = isMoreThanTenPercentAway(
            value = 11.1,
            currentValue = 10.0,
        )

        assertTrue(result)
    }

    @Test
    fun `given value more than 10 percent below current when comparing then returns true`() {
        val result = isMoreThanTenPercentAway(
            value = 8.9,
            currentValue = 10.0,
        )

        assertTrue(result)
    }

    @Test
    fun `given value exactly 10 percent above current when comparing then returns false`() {
        val result = isMoreThanTenPercentAway(
            value = 11.0,
            currentValue = 10.0,
        )

        assertFalse(result)
    }

    @Test
    fun `given value exactly 10 percent below current when comparing then returns false`() {
        val result = isMoreThanTenPercentAway(
            value = 9.0,
            currentValue = 10.0,
        )

        assertFalse(result)
    }

    @Test
    fun `given value equal to current when comparing then returns false`() {
        val result = isMoreThanTenPercentAway(
            value = 10.0,
            currentValue = 10.0,
        )

        assertFalse(result)
    }

    @Test
    fun `given current value is zero when comparing then returns false`() {
        val result = isMoreThanTenPercentAway(
            value = 10.0,
            currentValue = 0.0,
        )

        assertFalse(result)
    }
}