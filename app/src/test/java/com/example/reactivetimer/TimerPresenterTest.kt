package com.example.reactivetimer

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class TimerPresenterTest {

    private lateinit var presenter: TimerPresenter

    @Before
    fun setup() {
        presenter = TimerPresenter()
    }

    @Test
    fun `shows minutes, seconds & milliseconds`() {
        val timeInMilliseconds = 90200L

        val viewModel = presenter.format(timeInMilliseconds)

        assertEquals("1:30:2", viewModel.currentTime)
    }

    @Test
    fun `shows seconds & milliseconds`() {
        val timeInMilliseconds = 59500L

        val viewModel = presenter.format(timeInMilliseconds)

        assertEquals("59:5", viewModel.currentTime)
    }

    @Test
    fun `shows seconds & milliseconds even when seconds less than 1`() {
        val timeInMilliseconds = 500L

        val viewModel = presenter.format(timeInMilliseconds)

        assertEquals("0:5", viewModel.currentTime)
    }

    @Test
    fun `shows initial state correctly`() {
        val timeInMilliseconds = 120000L

        val viewModel = presenter.format(timeInMilliseconds)

        assertEquals("2:0:0", viewModel.currentTime)
    }

    @Test
    fun `returns state as normal`() {
        val timeInMilliseconds = 90200L

        val viewModel = presenter.format(timeInMilliseconds)

        assertTrue("viewModel is not TimerViewModel.Normal", viewModel is TimerViewModel.Normal)
    }

    @Test
    fun `returns state as warning`() {
        val lessThan30Seconds = 29900L

        val viewModel = presenter.format(lessThan30Seconds)

        assertTrue("viewModel is not TimerViewModel Warning", viewModel is TimerViewModel.Warning)
    }

    @Test
    fun `returns state as danger`() {
        val lessThan10Seconds = 9100L

        val viewModel = presenter.format(lessThan10Seconds)

        assertTrue("viewModel is not TimerViewModel.Danger", viewModel is TimerViewModel.Danger)
    }

    @Test
    fun `returns state as Done`(){
        val timeInMilliseconds = 0L

        val viewModel = presenter.format(timeInMilliseconds)

        assertTrue("viewModel is not TimerViewModel.Done", viewModel is TimerViewModel.Done)
    }
}

class TimerPresenter {

    fun format(timeInMilliseconds: Long): TimerViewModel {
        val minutes = timeInMilliseconds.toMinutes()
        val seconds = timeInMilliseconds.toSeconds()

        val formattedTime = formatString(minutes, seconds, timeInMilliseconds)

        return when {
            timeInMilliseconds <= 0 -> TimerViewModel.Done("Done!")
            seconds < 10 -> TimerViewModel.Danger(formattedTime)
            seconds < 30 -> TimerViewModel.Warning(formattedTime)
            else -> TimerViewModel.Normal(formattedTime)
        }
    }

    private fun formatString(minutes: Long, seconds: Long, milliSeconds: Long): String {
        val minutes = if (minutes > 0) "$minutes:" else ""
        return "$minutes${seconds % 60 }:${(milliSeconds % 1000) / 100 }"
    }

    private fun Long.toMinutes(): Long {
        return TimeUnit.MILLISECONDS.toMinutes(this)
    }

    private fun Long.toSeconds(): Long {
        return TimeUnit.MILLISECONDS.toSeconds(this)
    }
}

sealed class TimerViewModel(val currentTime: String) {

    class Normal(currentTime: String): TimerViewModel(currentTime)
    class Warning(currentTime: String): TimerViewModel(currentTime)
    class Danger(currentTime: String): TimerViewModel(currentTime)
    class Done(currentTime: String): TimerViewModel(currentTime)
}