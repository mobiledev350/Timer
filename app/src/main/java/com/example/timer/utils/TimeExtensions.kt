package com.example.timer.utils

private const val PATTERN = "%d:%02d:%02d"

fun Long.format(): String {
    val timeInSeconds = this / 1000
    val seconds = timeInSeconds % 60
    val minutes = (timeInSeconds - seconds) / 60 % 60
    val hours = (timeInSeconds - seconds - minutes * 60) / 360 * 60
    return PATTERN.format(hours, minutes, seconds)
}
