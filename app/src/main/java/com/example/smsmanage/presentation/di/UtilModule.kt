package com.example.smsmanage.presentation.di

import android.content.Context
import com.example.smsmanage.presentation.util.MessageBuilder
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
}