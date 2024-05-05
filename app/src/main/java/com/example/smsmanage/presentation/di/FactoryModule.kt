package com.example.smsmanage.presentation.di

import android.app.Application
import com.example.smsmanage.presentation.viewmodel.MainViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class FactoryModule {
    @ActivityScoped
    @Provides
    fun provideMainViewModelFactory(
        app: Application
    ): MainViewModelFactory {
        return MainViewModelFactory(app)
    }

}