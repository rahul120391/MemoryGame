package com.example.colourmemory.db.entity

import androidx.room.PrimaryKey
import androidx.room.Entity
import com.example.colourmemory.db.constants.DatabaseConstants

/**
 * Scores table containing the player details who has successfully completed game.
 */
@Entity(tableName = DatabaseConstants.TABLE_NAME)
data class ScoresEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name:String,
    var score:Int
)