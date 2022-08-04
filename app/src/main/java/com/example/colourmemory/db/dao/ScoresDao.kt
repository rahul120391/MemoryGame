package com.example.colourmemory.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.colourmemory.db.constants.DatabaseConstants
import com.example.colourmemory.db.entity.ScoresEntity
import com.example.colourmemory.model.Scores

@Dao
interface ScoresDao {

    @Query(DatabaseConstants.FETCH_SCORE_DATA)
    fun getAllScores():List<Scores>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHighScore(scoresEntity: ScoresEntity): Long
}