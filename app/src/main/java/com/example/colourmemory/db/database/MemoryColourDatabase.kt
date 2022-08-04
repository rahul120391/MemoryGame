package com.example.colourmemory.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.colourmemory.db.constants.DatabaseConstants
import com.example.colourmemory.db.dao.ScoresDao
import com.example.colourmemory.db.entity.ScoresEntity

@Database(entities = [ScoresEntity::class], version = DatabaseConstants.DATABASE_VERSION, exportSchema = false)
abstract class MemoryColourDatabase: RoomDatabase() {
    abstract fun getScoresDao():ScoresDao
}