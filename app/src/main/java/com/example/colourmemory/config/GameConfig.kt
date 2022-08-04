package com.example.colourmemory.config

import android.util.Pair
import android.util.SparseIntArray
import java.util.HashMap
import kotlin.math.sqrt


class GameConfig(
    numOfTiles: Int, val maxNoOfCardFlips: Int
) {

    val rowAndColPair: Pair<Int, Int> = Pair(
        sqrt(numOfTiles.toDouble()).toInt(), sqrt(
            numOfTiles.toDouble()
        ).toInt()
    )
    private val pairsMap: HashMap<Int, Int?> = HashMap()
    private val tileImageIdMap = SparseIntArray()
    fun putInPair(id: Int, key: Int) {
        pairsMap[id] = key
    }

    fun putInTileImage(id: Int, drawableId: Int) {
        tileImageIdMap.put(id, drawableId)
    }

    fun getDrawableId(id: Int): Int {
        return tileImageIdMap[id]
    }

    fun isMatched(pairOfFlippedIds: IntArray): Boolean {
        val firstId = pairOfFlippedIds[1]
        val secondId =
            if (pairsMap[pairOfFlippedIds[0]] != null) pairsMap[pairOfFlippedIds[0]]!! else Int.MIN_VALUE
        return firstId == secondId
    }

}