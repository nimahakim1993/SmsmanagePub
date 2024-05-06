package com.example.smsmanage.presentation.di

import com.example.smsmanage.data.info.InfoHelper
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
    fun provideMySmsReceiver(infoHelper: InfoHelper): MySmsReceiver {
        return MySmsReceiver(infoHelper)
    }
}