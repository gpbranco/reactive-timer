package com.example.reactivetimer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CountDownTimerFactory: ViewModelProvider.Factory {

    companion object {
        const val TOTAL_IN_MINUTES = 2L
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountDownTimerManager::class.java)) {
            return manager as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    val presenter by lazy {
        CountDownTimerPresenter(TOTAL_IN_MINUTES)
    }

    private val manager by lazy {
        CountDownTimerManager(TOTAL_IN_MINUTES, CountDownTimerRx(tick = CountDownTimerManager.TICK_DEFAULT))
    }
}