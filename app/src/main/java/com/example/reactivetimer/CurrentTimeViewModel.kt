package com.example.reactivetimer

sealed class CurrentTimeViewModel(val currentTime: String) {

    class Normal(currentTime: String): CurrentTimeViewModel(currentTime)
    class Warning(currentTime: String): CurrentTimeViewModel(currentTime)
    class Danger(currentTime: String): CurrentTimeViewModel(currentTime)
    class Done(currentTime: String): CurrentTimeViewModel(currentTime)
    class Ready(currentTime: String): CurrentTimeViewModel(currentTime)
}