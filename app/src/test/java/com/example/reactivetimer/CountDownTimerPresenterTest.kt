package com.example.reactivetimer

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class CountDownTimerPresenterTest {

    companion object {
        private const val TOTAL = 2L
    }

    private lateinit var presenterCountDown: CountDownTimerPresenter

    @Before
    fun setup() {
        presenterCountDown = CountDownTimerPresenter(TOTAL)
    }

    @Test
    fun `shows minutes, seconds & milliseconds`() {
        val timeInMilliseconds = 90200L

        val viewModel = presenterCountDown.format(timeInMilliseconds)

        assertEquals("1:30:2", viewModel.currentTime)
    }

    @Test
    fun `shows seconds & milliseconds`() {
        val timeInMilliseconds = 59500L

        val viewModel = presenterCountDown.format(timeInMilliseconds)

        assertEquals("59:5", viewModel.currentTime)
    }

    @Test
    fun `shows seconds & milliseconds even when seconds less than 1`() {
        val timeInMilliseconds = 500L

        val viewModel = presenterCountDown.format(timeInMilliseconds)

        assertEquals("0:5", viewModel.currentTime)
    }

    @Test
    fun `shows initial state correctly`() {
        val timeInMilliseconds = 120000L

        val viewModel = presenterCountDown.format(timeInMilliseconds)

        assertEquals("2:0:0", viewModel.currentTime)
    }

    @Test
    fun `returns state as normal`() {
        val timeInMilliseconds = 90200L

        val viewModel = presenterCountDown.format(timeInMilliseconds)

        assertTrue("viewModel is not CurrentTimeViewModel.Normal", viewModel is CurrentTimeViewModel.Normal)
    }

    @Test
    fun `returns state as warning`() {
        val lessThan30Seconds = 29900L

        val viewModel = presenterCountDown.format(lessThan30Seconds)

        assertTrue("viewModel is not CurrentTimeViewModel Warning", viewModel is CurrentTimeViewModel.Warning)
    }

    @Test
    fun `returns state as danger`() {
        val lessThan10Seconds = 9100L

        val viewModel = presenterCountDown.format(lessThan10Seconds)

        assertTrue("viewModel is not CurrentTimeViewModel.Danger", viewModel is CurrentTimeViewModel.Danger)
    }

    @Test
    fun `returns state as Done`() {
        val timeInMilliseconds = 0L

        val viewModel = presenterCountDown.format(timeInMilliseconds)

        assertTrue("viewModel is not CurrentTimeViewModel.Done", viewModel is CurrentTimeViewModel.Done)
    }

    @Test
    fun `returns state as Ready`() {
        val timeInMilliseconds = TimeUnit.MINUTES.toMillis(TOTAL)

        val viewModel = presenterCountDown.format(timeInMilliseconds)

        assertTrue("viewModel is not CurrentTimeViewModel.Ready", viewModel is CurrentTimeViewModel.Ready)
    }
}