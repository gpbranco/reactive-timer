package com.example.reactivetimer

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CountDownTimerRx(
    private val scheduler: Scheduler = Schedulers.computation(),
    private val tick: Long
) : CountDownTimer {

    override val stream: Flowable<Long>
        get() = Flowable
            .interval(tick, TimeUnit.MILLISECONDS, scheduler)
            .map { tick }
}