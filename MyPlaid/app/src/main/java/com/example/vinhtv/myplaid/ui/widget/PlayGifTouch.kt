package com.example.vinhtv.myplaid.ui.widget

import android.graphics.drawable.TransitionDrawable
import android.support.v7.widget.AppCompatImageView
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import com.bumptech.glide.load.resource.gif.GifDrawable

class PlayGifTouch: View.OnTouchListener {
    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        view?.performClick()
        if(view == null || event == null) return false
        if(view !is AppCompatImageView) return false
        val action = event.action
        if(!(action == ACTION_DOWN || action == ACTION_UP || action == ACTION_CANCEL)) return false

        val drawable = view.drawable ?: return false
        var gif: GifDrawable? = null
        if(drawable is GifDrawable) gif = drawable
        else if(drawable is TransitionDrawable) {
            // we fade in images on load which uses a TransitionDrawable; check its layers
            val fadingIn: TransitionDrawable = drawable
            for (i in 0..fadingIn.numberOfLayers) {
                if(fadingIn.getDrawable(i) is GifDrawable) {
                    gif = fadingIn.getDrawable(i) as GifDrawable
                    break
                }
            }
        }
        if(gif == null) return false
        when(action) {
            ACTION_DOWN -> gif.start()
            ACTION_CANCEL -> gif.stop()
        }
        return false
    }

}