package com.example.timer.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.timer.utils.Constants

interface TimeRepository {

    suspend fun setNewTime(time: Long)
    suspend fun getLastTime(): Long

}

internal class PreferencesTimeRepository(
    private val sharedPreferences: SharedPreferences
) : TimeRepository {

    override suspend fun setNewTime(time: Long) {
        sharedPreferences.edit {
            putLong(Constants.TIME_PREFERENCES_KEY, time)
        }
    }

    override suspend fun getLastTime(): Long {
        return sharedPreferences.getLong(Constants.TIME_PREFERENCES_KEY, 0)
    }

}