package com.example.reactivetimer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CountDownTimerFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountDownTimerManager::class.java)) {
            return manager as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    private val manager by lazy {
        CountDownTimerManager(2, CountDownTimerRx(tick = CountDownTimerManager.TICK_DEFAULT))
    }
}