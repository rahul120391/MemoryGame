package com.example.colourmemory.utils.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.example.colourmemory.views.customviews.GameCardView


fun View.visible(visible: Boolean){
    visibility = if(visible){
        View.VISIBLE
    }
    else{
        View.GONE
    }
}
fun View.invisible(){
    visibility = View.INVISIBLE
}

fun View.isGone():Boolean{
   return visibility == View.GONE
}

fun View.hideKeyboard(context: Context){
    val manager: InputMethodManager? = context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager?
    manager?.hideSoftInputFromWindow(
        windowToken, 0
    )
}

fun View.animateHide(){
    val animator = ObjectAnimator.ofFloat(this, "alpha", 0f).apply {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                setLayerType(LinearLayout.LAYER_TYPE_NONE, null)
                invisible()
            }
        })
        duration = 100
    }
    animator.start()
    setLayerType(LinearLayout.LAYER_TYPE_HARDWARE, null)
}

fun View.getObjectAnimator(scaleType:String,interpolator:TimeInterpolator = LinearInterpolator()):ObjectAnimator{
    val scaleAnimator = ObjectAnimator.ofFloat(this, scaleType, 0.8f, 1f)
    scaleAnimator.interpolator = interpolator
    return scaleAnimator
}

fun GameCardView.setImage(drawable:Int,width:Int,height:Int){
    Glide.with(context)
        .asBitmap()
        .load(drawable)
        .override(width, height)
        .into(object : CustomViewTarget<GameCardView?, Bitmap?>(this) {
            override fun onLoadFailed(errorDrawable: Drawable?) {

            }
            override fun onResourceCleared(placeholder: Drawable?) {}
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap?>?
            ) {
                getView().setCardBitmap(resource)
            }
        }).clearOnDetach()
}
