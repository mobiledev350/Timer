package com.example.timer.di

import android.content.Context
import com.example.timer.data.PreferencesTimeRepository
import com.example.timer.data.TimeRepository
import com.example.timer.utils.Constants
import com.example.timer.utils.Counter
import com.example.timer.utils.CounterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TimerModule {

    @Provides
    fun provideCounter(): Counter {
        return CounterImpl(interval = Constants.TIMER_INTERVAL_MS)
    }

    @Provides
    fun provideTimeRepository(@ApplicationContext context: Context): TimeRepository {
        return PreferencesTimeRepository(
            sharedPreferences = context.getSharedPreferences(
                Constants.TIME_PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        )
    }

}