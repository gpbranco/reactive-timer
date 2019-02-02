package com.example.reactivetimer

import java.util.concurrent.TimeUnit

class CountDownTimerPresenter {

    fun format(timeInMilliseconds: Long): CurrentTimeViewModel {
        val minutes = timeInMilliseconds.toMinutes()
        val seconds = timeInMilliseconds.toSeconds()

        val formattedTime = formatString(minutes, seconds, timeInMilliseconds)

        return when {
            timeInMilliseconds <= 0 -> CurrentTimeViewModel.Done("Done!")
            seconds < 10 -> CurrentTimeViewModel.Danger(formattedTime)
            seconds < 30 -> CurrentTimeViewModel.Warning(formattedTime)
            else -> CurrentTimeViewModel.Normal(formattedTime)
        }
    }

    private fun formatString(minutes: Long, seconds: Long, milliSeconds: Long): String {
        val minutesAsString = if (minutes > 0) "$minutes:" else ""
        return "$minutesAsString${seconds % 60 }:${(milliSeconds % 1000) / 100 }"
    }

    private fun Long.toMinutes(): Long {
        return TimeUnit.MILLISECONDS.toMinutes(this)
    }

    private fun Long.toSeconds(): Long {
        return TimeUnit.MILLISECONDS.toSeconds(this)
    }
}