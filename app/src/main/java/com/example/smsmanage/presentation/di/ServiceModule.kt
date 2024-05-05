package com.example.smsmanage.presentation.di

import com.example.smsmanage.data.receiver.MySmsReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {
    @Singleton
    @Provides
    fun provideMySmsReceiver(): MySmsReceiver {
        return MySmsReceiver()
    }
}