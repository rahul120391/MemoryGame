package com.example.colourmemory.di

import com.example.colourmemory.navigator.Navigator
import com.example.colourmemory.navigator.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ActivityModule {

    @Binds
    abstract fun provideNavigator(navigatorImpl: NavigatorImpl):Navigator
}