package com.example.colourmemory.views.customviews

import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.colourmemory.R
import com.example.colourmemory.config.GameConfig
import com.example.colourmemory.events.FlipEvent
import com.example.colourmemory.events.FlipEvent.Companion.getObject
import com.example.colourmemory.utils.extensions.animateHide
import com.example.colourmemory.utils.extensions.getObjectAnimator
import com.example.colourmemory.utils.extensions.setImage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit

class BoardView @JvmOverloads constructor(context: Context?, attributeSet: AttributeSet? = null) :
    LinearLayout(context, attributeSet), View.OnClickListener {
    private val rowLayoutParams =
        LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    private var tileLayoutParams: LayoutParams? = null
    private val screenWidth: Int
    private val screenHeight: Int
    private val viewReference: MutableMap<Int, GameCardView>
    private val flippedUpCardIds: MutableList<Int> = ArrayList()
    private var locked = false
    private var tilesHeight = 0
    private var tilesWidth = 0
    private var gameConfig: GameConfig? = null
    private var flippedCardObservable: PublishSubject<FlipEvent>? = null

    val flipEventObservable: Observable<FlipEvent>?
        get() = flippedCardObservable


    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        val margin = resources.getDimensionPixelSize(R.dimen._150sdp)
        val padding = resources.getDimensionPixelSize(R.dimen._10sdp)
        screenHeight = resources.displayMetrics.heightPixels - margin - padding * 2
        screenWidth = resources.displayMetrics.widthPixels - padding * 2 - resources.displayMetrics.density.toInt() * 20
        viewReference = HashMap()
        clipToPadding = false
    }

    fun setBoard(gameConfig: GameConfig) {
        flippedCardObservable = PublishSubject.create()
        this.gameConfig = gameConfig
        val singleMargin = 2*((2 * resources.displayMetrics.density).toInt())
        var sumMargin = 0
        for (row in 0 until gameConfig.rowAndColPair.first) {
            sumMargin += singleMargin * 2
        }
        tilesHeight = (screenHeight - sumMargin) / gameConfig.rowAndColPair.second
        tilesWidth = (screenWidth - sumMargin) / gameConfig.rowAndColPair.first
        tileLayoutParams = LayoutParams(tilesWidth, tilesHeight)
        tileLayoutParams?.setMargins(singleMargin, singleMargin, singleMargin, singleMargin)
        buildBoard()
    }
    
    private fun buildBoard() {
        val size = gameConfig?.rowAndColPair?.second?:0
        for (row in 0 until size) {
            addBoardRow(row, gameConfig)
        }
        clipChildren = false
    }
    private fun addBoardRow(rowNum: Int, config: GameConfig?) {
        val linearLayout = LinearLayout(context).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER
        }
        val size =  config?.rowAndColPair?.first?:0
        for (tile in 0 until size) {
            addTile(rowNum * (config?.rowAndColPair?.first?:0) + tile, linearLayout)
        }
        addView(linearLayout, rowLayoutParams)
        linearLayout.clipChildren = false
    }

    private fun addTile(id: Int, parent: ViewGroup) {
        val gameCardView = GameCardView(context).apply {
            layoutParams = tileLayoutParams
            tag = id
        }
        with(parent){
            addView(gameCardView)
            clipChildren = false
        }
        viewReference[id] = gameCardView
        with(gameCardView){
            gameConfig?.getDrawableId(id)?.let { setImage(it,tilesWidth, tilesHeight) }
            setOnClickListener(this@BoardView)
            setLayerType(LAYER_TYPE_HARDWARE, null)
        }
        with(AnimatorSet()){
            playTogether(gameCardView.getObjectAnimator("scaleX"), gameCardView.getObjectAnimator("scaleY"))
            duration = 500
            start()
        }
    }

    fun flipDownAll() {
        for (id in flippedUpCardIds) {
            viewReference[id]?.flipDown()
        }
        unlockCards()
    }

    private fun unlockCards() {
        flippedUpCardIds.clear()
        Observable.just(1)
            .delay(100, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { locked = false }
    }

    fun hideCards(arr: IntArray) {
        for (id in arr) {
            viewReference[id]?.animateHide()
        }
        unlockCards()
    }

    override fun onClick(view: View) {
        val gameCardView = view as GameCardView
        if (!locked && gameCardView.isFlippedDown) {
            gameCardView.flipUp()
            flippedUpCardIds.add(gameCardView.tag as Int)
            if (flippedUpCardIds.size == 2) {
                locked = true
            }
            flippedCardObservable?.onNext(getObject((gameCardView.tag as Int)))
        }
    }

    override fun onViewRemoved(child: View) {
        super.onViewRemoved(child)
        flippedCardObservable?.onComplete()
    }

    fun reset() {
        removeAllViews()
        locked = false
        viewReference.clear()
        flippedUpCardIds.clear()
    }
}