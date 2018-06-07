package com.example.android.myinstagram

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextSwitcher

class FeedItemAnimator {

    private val decelerate = DecelerateInterpolator()
    private val accelerate = AccelerateInterpolator()
    private val overshot = OvershootInterpolator()

    fun animateHeartButton(btnLike: ImageView, onEnd: () -> Unit) {
        val animatorSet = AnimatorSet()

        val rotationAnim = ObjectAnimator.ofFloat(btnLike, "rotation", 0f, 360f)
        rotationAnim.duration = 300
        rotationAnim.interpolator = accelerate

        val bounceAnimX = ObjectAnimator.ofFloat(btnLike, "scaleX", 0.2f, 1f)
        bounceAnimX.duration = 300
        bounceAnimX.interpolator = overshot

        val bounceAnimY = ObjectAnimator.ofFloat(btnLike, "scaleY", 0.2f, 1f)
        bounceAnimY.duration = 300
        bounceAnimY.interpolator = overshot
        bounceAnimY.addListener(object: AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                btnLike.setImageResource(R.drawable.ic_heart_red)
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                onEnd()
            }
        })

        animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim)
        animatorSet.start()
    }

    fun animateLikeCounter(likeCount: TextSwitcher, toValue: Int) {
        val likeCountTextFrom = "${toValue - 1} likes"
        val likeCountTextTo = "$toValue likes"
        likeCount.setCurrentText(likeCountTextFrom)
        likeCount.setText(likeCountTextTo)
    }

}