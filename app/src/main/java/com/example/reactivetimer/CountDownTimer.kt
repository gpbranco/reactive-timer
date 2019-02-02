package com.example.reactivetimer

import io.reactivex.Flowable

interface CountDownTimer {

    val stream: Flowable<Long>
}