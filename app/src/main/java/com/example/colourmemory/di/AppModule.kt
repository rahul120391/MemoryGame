package com.example.colourmemory.di

import android.content.Context
import androidx.room.Room
import com.example.colourmemory.db.constants.DatabaseConstants
import com.example.colourmemory.db.database.MemoryColourDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideMemoryColurDatabase(@ApplicationContext context:Context): MemoryColourDatabase {
        return Room.databaseBuilder(context, MemoryColourDatabase::class.java, DatabaseConstants.DATABASE_NAME).
        fallbackToDestructiveMigration().
        build()
    }
}