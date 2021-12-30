package com.example.timer.ui.timer

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.sharp.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timer.utils.format

@Composable
fun TimerScreen(viewModel: TimerViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    viewModel.attachToLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.background
                    ),
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimerDisplay(modifier = Modifier.fillMaxWidth(0.5f), time = state.currentTime)
        Spacer(modifier = Modifier.size(24.dp))
        TimerButton(
            modifier = Modifier.fillMaxWidth(0.5f),
            isRunning = state.isRunning
        ) {
            viewModel.onPlayClick()
        }
    }
}

@Composable
fun TimerDisplay(modifier: Modifier = Modifier, time: Long) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(
                MaterialTheme.colors.primary
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = time.format(), style = MaterialTheme.typography.h3)
    }
}

@Composable
fun TimerButton(modifier: Modifier, isRunning: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
    ) {
        Icon(
            imageVector = if (isRunning) {
                Icons.Sharp.Clear
            } else {
                Icons.Filled.PlayArrow
            }, contentDescription = null
        )
    }
}

@SuppressLint("ComposableNaming")
@Composable
private fun TimerViewModel.attachToLifecycle() {
    LocalLifecycleOwner.current.lifecycle.addObserver(this)
}
