package com.example.reactivetimer

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class CountDownTimerManager(totalInMinutes: Long, private val countDownTimer: CountDownTimer): ViewModel() {

    companion object {
        const val TICK_DEFAULT = 100L
    }

    val currentTime = BehaviorSubject.createDefault<Long>(TimeUnit.MINUTES.toMillis(totalInMinutes))
    private val totalInMilliSeconds = TimeUnit.MINUTES.toMillis(totalInMinutes)
    private var remainingTime = TimeUnit.MINUTES.toMillis(totalInMinutes)

    var compositeDisposable = CompositeDisposable()

    fun start() {
        countDownTimer.stream
            .repeatUntil {
                remainingTime < 0
            }
            .subscribe {

                remainingTime -= it
                currentTime.onNext(remainingTime)

            }.addTo(compositeDisposable)
    }

    fun moreTime(add: Int = 10) {
        val plusMilliSeconds = TimeUnit.SECONDS.toMillis(add.toLong())
        remainingTime = if (remainingTime + plusMilliSeconds > totalInMilliSeconds) totalInMilliSeconds
        else remainingTime + plusMilliSeconds
    }

    override fun onCleared() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onCleared()
    }
}