package com.example.timer.ui.timer

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timer.data.TimeRepository
import com.example.timer.utils.Constants
import com.example.timer.utils.Counter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val counter: Counter,
    private val timeRepository: TimeRepository
) : ViewModel(), DefaultLifecycleObserver {

    private val _state = MutableStateFlow(TimerState.default())
    val state: StateFlow<TimerState> = _state

    init {
        initStartTime()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        if (state.value.isRunning) {
            startTimer()
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        if (state.value.isRunning) {
            counter.stop()
        }
    }

    fun onPlayClick() {
        if (state.value.isRunning) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        _state.update {
            it.copy(isRunning = true)
        }
        counter.start(viewModelScope, state.value.currentTime) { currentTime ->
            timeRepository.setNewTime(currentTime)
            _state.update {
                it.copy(currentTime = currentTime)
            }
        }
    }

    private fun stopTimer() {
        _state.update {
            it.copy(isRunning = false, currentTime = Constants.ZERO_TIME)
        }
        counter.stop()
        viewModelScope.launch {
            timeRepository.setNewTime(Constants.ZERO_TIME)
        }
    }

    private fun initStartTime() {
        viewModelScope.launch {
            val lastTime = timeRepository.getLastTime()
            _state.update {
                it.copy(currentTime = lastTime)
            }
        }
    }

}
