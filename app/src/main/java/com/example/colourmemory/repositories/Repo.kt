package com.example.colourmemory.repositories

import com.example.colourmemory.db.database.MemoryColourDatabase
import com.example.colourmemory.db.entity.ScoresEntity
import com.example.colourmemory.model.PlayerDetails
import com.example.colourmemory.model.Scores
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

interface PlayerDataRepo{
    fun insertPlayerDetails(playerDetails: PlayerDetails):Single<Long>
    fun getScores(): Single<List<Scores>>
}

class PlayerDataRepoImpl @Inject constructor(private val memoryColourDatabase: MemoryColourDatabase):PlayerDataRepo{

    override fun getScores(): Single<List<Scores>> {
        return Single.just(memoryColourDatabase.getScoresDao())
            .subscribeOn(Schedulers.io()).map {
                it.getAllScores()
            }
    }

    override fun insertPlayerDetails(playerDetails: PlayerDetails): Single<Long> {
        return Single.just(memoryColourDatabase.getScoresDao()).subscribeOn(Schedulers.io()).map {
            it.insertHighScore(ScoresEntity(name = playerDetails.name, score = playerDetails.score))
        }
    }
}