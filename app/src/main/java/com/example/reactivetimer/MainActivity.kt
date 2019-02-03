package com.example.reactivetimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var presenter: CountDownTimerPresenter
    private lateinit var countDownTimerManager: CountDownTimerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies()

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

    private fun injectDependencies() {
        CountDownTimerFactory().also {
            countDownTimerManager = ViewModelProviders.of(this, it).get(CountDownTimerManager::class.java)
            presenter = it.presenter
        }
    }

    private fun showRemainingTime(viewModel: CurrentTimeViewModel) {
        remainingTime.text = viewModel.currentTime
        startButton.visibility = View.GONE

        when (viewModel) {
            is CurrentTimeViewModel.Warning -> content.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWarning))
            is CurrentTimeViewModel.Danger -> content.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDanger))
            is CurrentTimeViewModel.Normal -> content.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            is CurrentTimeViewModel.Ready, is CurrentTimeViewModel.Done -> {
                startButton.visibility = View.VISIBLE
                content.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            }
        }
    }
}
