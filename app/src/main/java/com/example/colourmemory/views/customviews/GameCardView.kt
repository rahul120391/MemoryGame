package com.example.colourmemory.views.customviews

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Camera
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.example.colourmemory.R
import com.example.colourmemory.utils.extensions.isGone
import com.example.colourmemory.utils.extensions.visible

class GameCardView(context: Context) :
    FrameLayout(context, null, -1) {
    private var topImage: AppCompatImageView? = null
    private var tileImage: AppCompatImageView? = null
    var isFlippedDown = true
    private set


    init {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        tileImage = AppCompatImageView(context).apply {
            layoutParams = params
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        addView(tileImage)
        topImage = AppCompatImageView(context).apply {
            layoutParams = params
            scaleType = ImageView.ScaleType.CENTER_CROP
            setImageDrawable(
                ContextCompat.getDrawable(context,R.drawable.card_bg)
            )
        }
        addView(topImage)
    }

    fun setCardBitmap(bitmap: Bitmap?) {
        tileImage?.setImageBitmap(bitmap)
    }

    fun flipUp() {
        isFlippedDown = false
        flip()
    }


    fun flipDown() {
        isFlippedDown = true
        flip()
    }

    private fun flip() {
        val flipAnimation = FlipAnimation(topImage, tileImage)
        if (topImage?.isGone() == true) {
            flipAnimation.reverse()
        }
        startAnimation(flipAnimation)
    }


    private inner class FlipAnimation(private var fromView: View?, private var toView: View?) :
        Animation() {
        private var camera: Camera? = null
        private var centerX = 0f
        private var centerY = 0f
        private var forward = true
        init {
            duration = 700
            fillAfter = false
            interpolator = AccelerateDecelerateInterpolator()
        }
        fun reverse() {
            forward = false
            val switchView = toView
            toView = fromView
            fromView = switchView
        }

        override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
            super.initialize(width, height, parentWidth, parentHeight)
            centerX = (width / 2).toFloat()
            centerY = (height / 2).toFloat()
            camera = Camera()
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            val radians = Math.PI * interpolatedTime
            var degrees = (180.0 * radians / Math.PI).toFloat()

            if (interpolatedTime >= 0.5f) {
                degrees -= 180f
                fromView?.visible(false)
                toView?.visible(true)
            }
            if (forward) degrees = -degrees
            val matrix = t.matrix
            camera?.run {
                save()
                rotateX(degrees)
                getMatrix(matrix)
                restore()
            }
            with(matrix){
                preTranslate(-centerX, -centerY)
                postTranslate(centerX, centerY)
            }

        }

    }
}