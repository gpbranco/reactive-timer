# Reactive-Timer

The app is a simple countdown timer written in Kotlin.

It starts with an initial value of 2 minutes and counts down to zero, showing different states depending on the current time:

* time >= 30 seconds -> Regular
* time >= 10 & time < 30 seconds -> Warning
* time <= 10 seconds -> Danger

It uses [RxJava](https://github.com/ReactiveX/RxJava) in order to implement the timer and [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) (provided by Google Architecture Components) to keep state during configuration changes.