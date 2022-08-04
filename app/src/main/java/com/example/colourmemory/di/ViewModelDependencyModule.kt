package com.example.colourmemory.di

import com.example.colourmemory.repositories.PlayerDataRepo
import com.example.colourmemory.repositories.PlayerDataRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelDependencyModule {

    @Binds
    abstract fun providePlayerDataRepoDependency(playerDataRepoImpl: PlayerDataRepoImpl):PlayerDataRepo
}