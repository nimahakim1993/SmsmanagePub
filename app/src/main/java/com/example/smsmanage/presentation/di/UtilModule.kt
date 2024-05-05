package com.example.smsmanage.presentation.di

import android.content.Context
import com.example.smsmanage.presentation.util.MessageBuilder
import com.example.smsmanage.presentation.util.MessageValidator
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
    fun provideMessageValidator(@ApplicationContext context: Context): MessageValidator {
        return MessageValidator(context)
    }
    @Singleton
    @Provides
    fun providePermissionManager(@ApplicationContext context: Context): PermissionManager {
        return PermissionManager(context)
    }
}