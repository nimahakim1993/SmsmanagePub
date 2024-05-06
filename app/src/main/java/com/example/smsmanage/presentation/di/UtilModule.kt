package com.example.smsmanage.presentation.di

import android.content.Context
import com.example.smsmanage.data.info.InfoHelper
import com.example.smsmanage.presentation.util.MessageBuilder
import com.example.smsmanage.presentation.util.DataValidator
import com.example.smsmanage.presentation.util.NotificationManager
import com.example.smsmanage.presentation.util.PermissionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UtilModule {

    @Singleton
    @Provides
    fun provideMessageBuild(@ApplicationContext context: Context): MessageBuilder {
        return MessageBuilder(context)
    }
    @Singleton
    @Provides
    fun provideMessageValidator(@ApplicationContext context: Context): DataValidator {
        return DataValidator(context)
    }
    @Singleton
    @Provides
    fun providePermissionManager(@ApplicationContext context: Context): PermissionManager {
        return PermissionManager(context)
    }
    @Singleton
    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return NotificationManager(context)
    }
    @Singleton
    @Provides
    fun provideInfoHelper(@ApplicationContext context: Context): InfoHelper {
        return InfoHelper(context)
    }
}