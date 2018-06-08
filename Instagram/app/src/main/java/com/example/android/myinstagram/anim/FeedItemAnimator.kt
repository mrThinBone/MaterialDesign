package com.example.android.myinstagram.anim

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextSwitcher
import com.example.android.myinstagram.R
import com.example.android.myinstagram.Utils
import com.example.android.myinstagram.adapter.FeedAdapter

class FeedItemAnimator: DefaultItemAnimator() {

    private val decelerate = DecelerateInterpolator()
    private val accelerate = AccelerateInterpolator()
    private val overshot = OvershootInterpolator()

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        runEnterAnimation(holder = holder as FeedAdapter.FeedViewHolder)
        return false
    }

    override fun animateChange(oldHolder: RecyclerView.ViewHolder, newHolder: RecyclerView.ViewHolder, preInfo: ItemHolderInfo, postInfo: ItemHolderInfo): Boolean {
        val holder = newHolder as FeedAdapter.FeedViewHolder
        animateHeartButton(holder.vBtnLike)
        animateLikeCounter(holder.vLikeCount, holder.feedItem!!.likeCount)
        return false
    }

    private fun runEnterAnimation(holder: FeedAdapter.FeedViewHolder) {
        val screenWidth = Utils.getScreenWidth(holder.itemView.context).toFloat()
        val screenHeight = Utils.getScreenHeight(holder.itemView.context).toFloat()
        holder.itemView.translationY = screenHeight
        holder.itemView.animate()
                .translationY(0f)
                .setInterpolator(DecelerateInterpolator(3.0f))
                .setDuration(500)
                .start()
    }

    private fun animateHeartButton(btnLike: ImageView) {
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
        })

        animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim)
        animatorSet.start()
    }

    private fun animateLikeCounter(likeCount: TextSwitcher, toValue: Int) {
        val likeCountTextFrom = "${toValue - 1} likes"
        val likeCountTextTo = "$toValue likes"
        likeCount.setCurrentText(likeCountTextFrom)
        likeCount.setText(likeCountTextTo)
    }

}