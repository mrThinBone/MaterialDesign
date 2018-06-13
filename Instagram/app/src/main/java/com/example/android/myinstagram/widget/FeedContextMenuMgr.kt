package com.example.android.myinstagram.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import com.example.android.myinstagram.Utils

class FeedContextMenuMgr private constructor() : RecyclerView.OnScrollListener(), View.OnAttachStateChangeListener {

    private var contextMenuView: FeedContextMenu? = null
    private var isContextMenuShowing = false
    private var isContextMenuDismissing = false

    object Singleton {
        var instance = FeedContextMenuMgr()
    }

    override fun onViewAttachedToWindow(v: View?) {}

    override fun onViewDetachedFromWindow(v: View?) {
        contextMenuView = null
    }

    fun toggleContextMenu(openingView: View, listener: View.OnClickListener) {
        if(contextMenuView == null) showContextMenu(openingView, listener)
        else hideContextMenu()
    }


    fun showContextMenu(openingView: View, listener: View.OnClickListener) {
        if(isContextMenuShowing) return
        isContextMenuShowing = true
        contextMenuView = FeedContextMenu(openingView.context, listener)
        with(contextMenuView!!) {
            addOnAttachStateChangeListener(this@FeedContextMenuMgr)
            openingView.rootView.findViewById<ViewGroup>(android.R.id.content).addView(contextMenuView)
            viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    viewTreeObserver.removeOnPreDrawListener(this)
                    settingContextMenuLocation(openingView)
                    performShowAnimation()
                    return false
                }
            })
        }
    }

    fun hideContextMenu() {
        if(isContextMenuDismissing) return
        isContextMenuDismissing = true
        performDismissAnimation()
    }

    private fun settingContextMenuLocation(openingView: View) {
        contextMenuView?.let {
            val openViewLocation = intArrayOf(0,0)
            openingView.getLocationOnScreen(openViewLocation)
            val additionalBottomMargin = Utils.dpToPx(16).toFloat()
            it.translationX = openViewLocation[0] - it.width / 3f
            it.translationY = openViewLocation[1] - it.height - additionalBottomMargin
        }
    }

    private fun performShowAnimation() {
        if(contextMenuView == null) return
        with(contextMenuView!!) {
            pivotX = width / 2f
            pivotY = height.toFloat()
            scaleX = 0.1f
            scaleY = 0.1f
            animate().scaleX(1f).scaleY(1f)
                    .setDuration(300)
                    .setInterpolator(DecelerateInterpolator(2f))
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            isContextMenuShowing = false
                        }
                    }).start()
        }
    }

    private fun performDismissAnimation() {
        if(contextMenuView == null) return
        with(contextMenuView!!) {
            pivotX = width / 2f
            pivotY = height.toFloat()
            animate().scaleX(1f).scaleY(1f)
                    .setDuration(150)
                    .setInterpolator(OvershootInterpolator())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            contextMenuView?.dismiss()
                            isContextMenuDismissing = false
                        }
                    }).start()
        }
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        contextMenuView?.let{
            hideContextMenu()
            it.translationY = it.translationY - dy
        }
    }
}