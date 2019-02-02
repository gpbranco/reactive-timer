package com.example.reactivetimer

import com.nhaarman.mockito_kotlin.mock
import io.reactivex.BackpressureStrategy
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.Mock
import java.util.concurrent.TimeUnit

class CountDownTimerManagerTest {

    companion object {
        private const val TOTAL_IN_MINUTES = 2L
        private const val ONE_SECOND_IN_MILLISECONDS = 1000L
    }

    @Mock
    lateinit var timer: CountDownTimer

    lateinit var manager: CountDownTimerManager

    lateinit var producer: PublishSubject<Long>

    @Before
    fun setup() {
        producer = PublishSubject.create<Long>()
        timer = mock()
        given(timer.stream).willReturn(producer.toFlowable(BackpressureStrategy.LATEST))
        manager = CountDownTimerManager(TOTAL_IN_MINUTES, countDownTimer = timer)
    }

    @Test
    fun `emits default value as first value`() {
        val testObserver = manager.currentTime.test()

        testObserver.assertValue(TimeUnit.MINUTES.toMillis(TOTAL_IN_MINUTES))
    }

    @Test
    fun `adds more time up to initial time`() {
        val testObserver = manager.currentTime.test()
        manager.start()

        manager.moreTime(100)

        producer.onNext(ONE_SECOND_IN_MILLISECONDS)

        testObserver.assertValueCount(2)
        testObserver.assertValueAt(1) {
            it < TimeUnit.MINUTES.toMillis(TOTAL_IN_MINUTES)
        }
    }

    @Test
    fun `adds more time`() {
        val tenSecondsPassed = 10000L
        val addFiveSeconds = 5000L
        val expectedTime =
            TimeUnit.MINUTES.toMillis(TOTAL_IN_MINUTES) - tenSecondsPassed + addFiveSeconds - ONE_SECOND_IN_MILLISECONDS

        val testObserver = manager.currentTime.test()

        manager.start()

        producer.onNext(tenSecondsPassed)

        manager.moreTime(TimeUnit.MILLISECONDS.toSeconds(addFiveSeconds).toInt())

        producer.onNext(ONE_SECOND_IN_MILLISECONDS)

        testObserver.assertLast(expectedTime)
    }

    private fun TestObserver<Long>.assertLast(expected: Long) {
        Assert.assertEquals(expected, this.values().last())
    }
}