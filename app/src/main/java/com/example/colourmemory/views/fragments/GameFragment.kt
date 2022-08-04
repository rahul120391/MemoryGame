package com.example.colourmemory.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.colourmemory.R
import com.example.colourmemory.base.BaseFragment
import com.example.colourmemory.databinding.FragmentGameBinding
import com.example.colourmemory.enums.EventType
import com.example.colourmemory.events.HideCardEvent
import com.example.colourmemory.events.ScoreUpdateEvent
import com.example.colourmemory.navigator.Navigator
import com.example.colourmemory.utils.Utility
import com.example.colourmemory.utils.extensions.popToPreviousFragment
import com.example.colourmemory.utils.extensions.viewBinding
import com.example.colourmemory.utils.extensions.visible
import com.example.colourmemory.viewmodels.GameViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import javax.inject.Inject

@AndroidEntryPoint
class GameFragment:BaseFragment(R.layout.fragment_game) {

    @Inject
    lateinit var navigator: Navigator

    private val viewModel by viewModels<GameViewModel>()
    private val binding by viewBinding(FragmentGameBinding::bind)
    override fun getFragmentTag(): String = TAG

    private var drawables = listOf(R.drawable.colour1,
        R.drawable.colour2,R.drawable.colour3,R.drawable.colour4,
        R.drawable.colour5,R.drawable.colour6,R.drawable.colour7,
        R.drawable.colour8)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            setScoreText(getString(R.string.zero))
            setListener()
            initObservers()
        }
    }

    fun checkGameState(){
        if (viewModel.hasPlayerMadeMove){
            Utility.showDialog(
                WeakReference(context), getString(R.string.quit),
                getString(R.string.are_u_sure_u_want_to_quit),
                getString(R.string.ok),getString(R.string.cancel)){
                activity?.popToPreviousFragment()
            }
        }
        else{
           activity?.popToPreviousFragment()
        }
    }

    private fun FragmentGameBinding.setListener(){
        imgScore.setOnClickListener {
           navigator.moveToScoreListFragment()
        }
    }

    private fun FragmentGameBinding.initObservers(){
        with(viewModel){
            startGame(drawables.toMutableList())
            gameConfig.observe(viewLifecycleOwner){
                  gameConfig->
                  boardView.setBoard(gameConfig)
                  observeFlipEvent(boardView.flipEventObservable)
            }
            gameEvents.observe(viewLifecycleOwner){
               event->
                when(event.eventType){
                    EventType.FlipCardDownEvent->{
                        boardView.flipDownAll()
                    }
                    EventType.GameWonEvent->{
                        val scoreEvent = event as ScoreUpdateEvent
                        setScoreText(scoreEvent.score.toString())
                        checkIfGameFinished(scoreEvent)
                    }
                    EventType.HideCardEvent->{
                         boardView.hideCards((event as HideCardEvent).pairOfFlippedIds)
                    }
                }
            }
        }
    }

    private fun setScoreText(score:String){
        binding.txtScore.text = getString(R.string.score).plus(":").plus(" ").plus(score)
    }

    private fun FragmentGameBinding.checkIfGameFinished(event:ScoreUpdateEvent){
        if(event.isGameFinished){
            txtGameFinished.visible(true)
            navigator.showUserInputDialog(this@GameFragment,event.score){
                boardView.reset()
                txtGameFinished.visible(false)
                setScoreText(getString(R.string.zero))
                navigator.moveToScoreListFragment()
                with(viewModel){
                    setPlayerMove(false)
                    startGame(drawables.toMutableList())
                }

            }
        }
    }

    companion object{
        const val TAG = "GameView"
        fun newInstance():GameFragment = GameFragment()
    }
}