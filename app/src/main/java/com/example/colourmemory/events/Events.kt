package com.example.colourmemory.events

import com.example.colourmemory.base.BaseEvent
import com.example.colourmemory.enums.EventType

class FlipCardDownEvent : BaseEvent(EventType.FlipCardDownEvent)

class FlipEvent(val idFlipped: Int) {

    companion object {
        fun getObject(id: Int): FlipEvent {
            return FlipEvent(id)
        }
    }
}
class HideCardEvent(val pairOfFlippedIds: IntArray) : BaseEvent(EventType.HideCardEvent)
class ScoreUpdateEvent : BaseEvent(EventType.GameWonEvent) {
    var score = 0
    var isGameFinished = false
}
