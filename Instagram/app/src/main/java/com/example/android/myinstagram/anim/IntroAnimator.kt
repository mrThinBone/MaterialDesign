package com.example.android.myinstagram.anim

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.support.design.widget.AppBarLayout
import android.widget.ImageView
import com.example.android.myinstagram.Utils

//https://stackoverflow.com/questions/30655939/programmatically-collapse-or-expand-collapsingtoolbarlayout
class IntroAnimator {

    fun start(appbar: AppBarLayout, logo: ImageView, contentAnimation: () -> Unit) {
        val actionBarHeight = Utils.dpToPx(56).toFloat()

        appbar.setExpanded(true, true)
        logo.translationY = -actionBarHeight

        logo.animate()
                .translationY(0f)
                .setDuration(300)
                .setStartDelay(200)
                .setListener(object: AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        contentAnimation()
                    }
                }).start()
    }

}