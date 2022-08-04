package com.example.colourmemory.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.colourmemory.base.BaseEvent
import com.example.colourmemory.config.BoardController
import com.example.colourmemory.config.GameConfig
import com.example.colourmemory.enums.GameTypeEnum
import com.example.colourmemory.events.FlipEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val boardController: BoardController):ViewModel(){

    init {
        boardController.initEventPublishers()
    }

    companion object{
        private const val DELAY = 1000L
    }

    private val _gameConfig = MutableLiveData<GameConfig>()
    val gameConfig:LiveData<GameConfig> = _gameConfig

    private val _gameEvents = MutableLiveData<BaseEvent>()
    val gameEvents:LiveData<BaseEvent> = _gameEvents

    private var compositeDisposable = CompositeDisposable()

    var hasPlayerMadeMove = false
    private set

    fun setPlayerMove(hasPlayerMadeMove:Boolean){
        this.hasPlayerMadeMove = hasPlayerMadeMove
    }

    fun startGame(resourceIds:MutableList<Int>){
        resetAll()
        compositeDisposable.add(boardController.startGameEventObservable?.
        delay(DELAY,TimeUnit.MILLISECONDS)?.
        observeOn(AndroidSchedulers.mainThread())?.
        subscribe({
             gameConfig->
             _gameConfig.value = gameConfig
            initBoardEventListeners()
        },{
           println("start game error = ${it.message}")
        })?: Disposable.empty())
        boardController.startGame(GameTypeEnum.DEFAULT_TILES,resourceIds)
    }

    private fun initBoardEventListeners(){
        compositeDisposable.add(boardController.
        flipDownEventObservable?.
        observeOn(AndroidSchedulers.mainThread())?.
        subscribe({
            event->
            _gameEvents.value = event
        },{
            println("flip down event error = ${it.message}")
        })?: Disposable.empty())
        compositeDisposable.add(boardController.hideEventObservable?.
                observeOn(AndroidSchedulers.mainThread())?.
                subscribe({
                    event->
                    _gameEvents.value = event
                },{
                    println("hide card event error = ${it.message}")
                })?: Disposable.empty())
        compositeDisposable.add(boardController.scoreUpdateEventObservable?.
        observeOn(AndroidSchedulers.mainThread())?.
        subscribe({
                event->
            _gameEvents.value = event
        },{
            println("score update event error = ${it.message}")
        })?: Disposable.empty())

    }

    fun observeFlipEvent(flipEventObservable:Observable<FlipEvent>?){
         compositeDisposable.add(flipEventObservable?.subscribe({
            event->
            boardController.tileFlipped(event)
            hasPlayerMadeMove = true
        },{
            println("flip event error = ${it.message}")
        })?: Disposable.empty())

    }
    private fun resetAll(){
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
        boardController.clearData()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        boardController.clearData()
    }
}