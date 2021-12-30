package com.example.timer.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface Counter {

    /**
     * Starts the timer.
     *
     * @param coroutineScope - CoroutineScope that will be used to start counter loop.
     * @param from - initial time offset.
     * @param onTick - callback that will be called each iteration. Contains current time including
     * offset
     */
    fun start(coroutineScope: CoroutineScope, from: Long, onTick: suspend (Long) -> Unit)
    fun stop()
}

internal class CounterImpl(
    private val interval: Long
) : Counter {

    var isRunning = false
        private set
    private var counterJob: Job? = null

    override fun start(coroutineScope: CoroutineScope, from: Long, onTick: suspend (Long) -> Unit) {
        counterJob?.cancel()
        isRunning = true
        counterJob = coroutineScope.launch {
            var elapsedTime = from
            val startTime = System.currentTimeMillis()
            var lastIterationTime = startTime
            while (isRunning) {
                onTick(elapsedTime)
                val now = System.currentTimeMillis()
                elapsedTime = now - startTime + from
                val time = now - lastIterationTime
                lastIterationTime = now
                if (time < interval) {
                    delay(interval - time)
                }
            }
        }
    }

    override fun stop() {
        isRunning = false
        counterJob?.cancel()
    }

}