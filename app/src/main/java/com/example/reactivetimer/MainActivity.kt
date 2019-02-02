package com.example.reactivetimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private val presenter = CountDownTimerPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countDownTimerManager = countDownTimerManager()

        countDownTimerManager.currentTime
            .map { presenter.format(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showRemainingTime(it)

            }.addTo(compositeDisposable)

        startButton.setOnClickListener {
            countDownTimerManager.start()
        }

        fab.setOnClickListener {
            countDownTimerManager.moreTime()
        }
    }

    private fun countDownTimerManager(): CountDownTimerManager {
        return ViewModelProviders.of(
            this,
            CountDownTimerFactory()
        ).get(CountDownTimerManager::class.java)
    }

    private fun showRemainingTime(viewModel: CurrentTimeViewModel) {
        remainingTime.text = viewModel.currentTime

        when (viewModel) {
            is CurrentTimeViewModel.Warning -> content.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWarning))
            is CurrentTimeViewModel.Danger -> content.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDanger))
            else -> content.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        }
    }
}
