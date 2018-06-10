package com.example.android.myinstagram.anim

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.view.animation.DecelerateInterpolator

class CommentItemAnimator: DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        holder?.itemView?.let {
            it.translationY = 100f
            it.animate()
                    .translationY(0f)
                    .setStartDelay(holder.adapterPosition*20L)
                    .setInterpolator(DecelerateInterpolator(2f))
                    .setDuration(300)
                    .start()
        }
        return false
    }
}