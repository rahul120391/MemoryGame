package com.example.colourmemory.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.colourmemory.model.PlayerDetails
import com.example.colourmemory.model.Scores
import com.example.colourmemory.repositories.PlayerDataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class PlayerDataViewModel @Inject constructor(private val playerDataRepo: PlayerDataRepo):ViewModel() {

    private val _onScoreListFetch = MutableLiveData<List<Scores>>()
    val onScoreListFetch:LiveData<List<Scores>> = _onScoreListFetch

    private val _onScoreInsertSuccess = MutableLiveData<Unit>()
    val onScoreInsertSuccess:LiveData<Unit> = _onScoreInsertSuccess

    private val _onError = MutableLiveData<String>()
    val onError:LiveData<String> = _onError

    private val compositeDisposable by lazy { CompositeDisposable() }

    fun fetchData(){
        compositeDisposable.add(playerDataRepo.getScores().observeOn(AndroidSchedulers.mainThread()).subscribe({
             compositeDisposable.dispose()
            _onScoreListFetch.value = it
        },{
            _onError.value = it.message
        }))
    }

    fun insertPlayerData(playerDetails: PlayerDetails){
        compositeDisposable.add(
            playerDataRepo.insertPlayerDetails(playerDetails).observeOn(AndroidSchedulers.mainThread()).subscribe({
                compositeDisposable.dispose()
                println("rowId = $it")
                _onScoreInsertSuccess.value = Unit
            },{
                _onError.value = it.message
            })
        )
    }
}