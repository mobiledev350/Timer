package com.example.timer.ui.timer

data class TimerState(
    val currentTime: Long,
    val isRunning: Boolean
) {

    companion object {

        fun default(): TimerState {
            return TimerState(
                currentTime = 0,
                isRunning = false
            )
        }

    }

}