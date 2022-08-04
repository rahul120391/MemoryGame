package com.example.colourmemory.config

import com.example.colourmemory.enums.GameTypeEnum
import com.example.colourmemory.events.FlipCardDownEvent
import com.example.colourmemory.events.FlipEvent
import com.example.colourmemory.events.HideCardEvent
import com.example.colourmemory.events.ScoreUpdateEvent
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ViewModelScoped
class BoardController @Inject constructor() {

    private var pairOfFlippedIds: IntArray?=null

    private var lastIndexOfPairIdFlipped = 0

    private var totalNoOfTilesToFlip = 0

    private var score = 0

    private var gameConfig: GameConfig? = null

    private var startGameObsEvent: PublishSubject<GameConfig>? = null
    private var flipCardDownEvent: PublishSubject<FlipCardDownEvent>? = null
    private var hideCardEvent: PublishSubject<HideCardEvent>? = null
    private var scoreUpdateEvent: PublishSubject<ScoreUpdateEvent>? = null


    fun startGame(gameType: GameTypeEnum, drawableIds: MutableList<Int>) {
        resetFlippedIdArray(gameType.maxNoOfFlips)
        gameConfig = GameConfig(gameType.tiles, gameType.maxNoOfFlips)
        totalNoOfTilesToFlip = gameType.tiles
        prepareGameData(drawableIds)
        gameConfig?.let {
            startGameObsEvent?.onNext(it)
        }

    }

    fun initEventPublishers() {
        startGameObsEvent = PublishSubject.create()
        flipCardDownEvent = PublishSubject.create()
        hideCardEvent = PublishSubject.create()
        scoreUpdateEvent = PublishSubject.create()
    }

    private fun prepareGameData(drawableIds: MutableList<Int>) {
        val noOfTilesToShuffle: MutableList<Int> = ArrayList()
        for (i in 0 until totalNoOfTilesToFlip) {
            noOfTilesToShuffle.add(i)
        }
        noOfTilesToShuffle.shuffle()
        drawableIds.shuffle()
        var j = 0
        var i = 0
        while (i < totalNoOfTilesToFlip) {
            if (i + 1 < totalNoOfTilesToFlip) {
                gameConfig?.putInPair(noOfTilesToShuffle[i], noOfTilesToShuffle[i + 1])
                gameConfig?.putInPair(noOfTilesToShuffle[i + 1], noOfTilesToShuffle[i])
                gameConfig?.putInTileImage(noOfTilesToShuffle[i], drawableIds[j])
                gameConfig?.putInTileImage(noOfTilesToShuffle[i + 1], drawableIds[j])
                i++
                j++
            }
            i++
        }
    }

    private fun resetFlippedIdArray(maxNoOfFlippedIds: Int) {
        pairOfFlippedIds = IntArray(maxNoOfFlippedIds)
        for (i in 0 until maxNoOfFlippedIds) {
            pairOfFlippedIds?.set(i,-1)
        }
        lastIndexOfPairIdFlipped = 0
    }

    val startGameEventObservable: Observable<GameConfig>?
        get() = startGameObsEvent
    val hideEventObservable: Observable<HideCardEvent>?
        get() = hideCardEvent?.delay(1, TimeUnit.SECONDS)
    val flipDownEventObservable: Observable<FlipCardDownEvent>?
        get() = flipCardDownEvent?.delay(1, TimeUnit.SECONDS)

    val scoreUpdateEventObservable: Observable<ScoreUpdateEvent>?
        get() = scoreUpdateEvent?.delay(1200, TimeUnit.MILLISECONDS)


    fun tileFlipped(flipEvent: FlipEvent) {
        if (lastIndexOfPairIdFlipped < (pairOfFlippedIds?.size?:0) - 1) {
            pairOfFlippedIds?.set(lastIndexOfPairIdFlipped,flipEvent.idFlipped)
            lastIndexOfPairIdFlipped++
        } else {
            pairOfFlippedIds?.set(lastIndexOfPairIdFlipped,flipEvent.idFlipped)
            lastIndexOfPairIdFlipped++
            val matched = pairOfFlippedIds?.let { gameConfig?.isMatched(it) }
            if (matched==true) {
                pairOfFlippedIds?.let {
                    hideCardEvent?.onNext(HideCardEvent(it))
                }
                totalNoOfTilesToFlip -= gameConfig?.maxNoOfCardFlips?:0
                score += 2
            } else {
                score -= 1
                flipCardDownEvent?.onNext(FlipCardDownEvent())
            }
            val event = ScoreUpdateEvent()
            event.isGameFinished = totalNoOfTilesToFlip == 0
            event.score = score
            scoreUpdateEvent?.onNext(event)
            gameConfig?.maxNoOfCardFlips?.let { resetFlippedIdArray(it) }
        }
    }


    fun clearData() {
        pairOfFlippedIds = null
        lastIndexOfPairIdFlipped = 0
        totalNoOfTilesToFlip = 0
        score = 0
    }
}