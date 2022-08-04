package com.example.colourmemory.utils

import android.graphics.Outline
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.IntDef
import com.example.colourmemory.utils.RadiusType.Companion.ALL
import com.example.colourmemory.utils.RadiusType.Companion.BOTTOM
import com.example.colourmemory.utils.RadiusType.Companion.BOTTOM_LEFT
import com.example.colourmemory.utils.RadiusType.Companion.BOTTOM_RIGHT
import com.example.colourmemory.utils.RadiusType.Companion.LEFT
import com.example.colourmemory.utils.RadiusType.Companion.RIGHT
import com.example.colourmemory.utils.RadiusType.Companion.TOP
import com.example.colourmemory.utils.RadiusType.Companion.TOP_LEFT
import com.example.colourmemory.utils.RadiusType.Companion.TOP_RIGHT

class CustomOutlineProvider(private val radius:Float, @RadiusType private val radiusType:Int = ALL): ViewOutlineProvider() {

    override fun getOutline(view: View?, outline: Outline?) {
        view?.run {
            val cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, resources?.displayMetrics).toInt()
            val left = 0
            val top = 0
            val right = width
            val bottom = height
            when(radiusType){
                ALL->{
                    outline?.setRoundRect(Rect(left,top, right, bottom),radius)
                }
                TOP->{
                    outline?.setRoundRect(Rect(left, top, right, bottom.plus(cornerRadius)),radius)
                }
                BOTTOM->{
                    outline?.setRoundRect(Rect(left, top-cornerRadius, right, bottom),radius)
                }
                LEFT->{
                    outline?.setRoundRect(Rect(left, top, right+cornerRadius, bottom),radius)
                }
                RIGHT->{
                    outline?.setRoundRect(Rect(left-cornerRadius, top, right, bottom),radius)
                }
                TOP_LEFT->{
                    outline?.setRoundRect(left , top, right+ cornerRadius, bottom + cornerRadius, radius)
                }
                BOTTOM_LEFT->{
                    outline?.setRoundRect(left, top - cornerRadius, right + cornerRadius, bottom, radius)
                }
                TOP_RIGHT->{
                    outline?.setRoundRect(left - cornerRadius , top, right, bottom + cornerRadius, radius)
                }
                BOTTOM_RIGHT->{
                    outline?.setRoundRect(left - cornerRadius, top - cornerRadius, right, bottom, radius)
                }
            }
            clipToOutline = true
        }

    }
}

@IntDef(ALL,TOP,BOTTOM,LEFT,RIGHT,TOP_LEFT,BOTTOM_LEFT,TOP_RIGHT,BOTTOM_RIGHT)
@Retention(AnnotationRetention.SOURCE)
annotation class RadiusType{
    companion object{
        const val ALL = 0
        const val TOP = 1
        const val BOTTOM = 2
        const val LEFT = 3
        const val RIGHT = 4
        const val TOP_LEFT = 5
        const val BOTTOM_LEFT = 6
        const val TOP_RIGHT = 7
        const val BOTTOM_RIGHT = 8

    }
}