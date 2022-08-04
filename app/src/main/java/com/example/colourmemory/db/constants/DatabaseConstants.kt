package com.example.colourmemory.db.constants

object DatabaseConstants {

    const val DATABASE_NAME = "MemoryGameDatabase"
    const val DATABASE_VERSION = 1
    const val TABLE_NAME = "Scores"

    const val FETCH_SCORE_DATA = "SELECT prev_score.name as name, prev_score.score as score, COUNT (distinct current_score.score) as rank " +
            "FROM scores prev_score, scores current_score WHERE prev_score.score < current_score.score OR " +
            "(prev_score.score=current_score.score AND prev_score.name = current_score.name) " +
            "GROUP BY prev_score.name, prev_score.score ORDER BY prev_score.score DESC"
}