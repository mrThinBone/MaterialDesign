package com.example.android.design1.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

abstract class OnItemSelectedListener(context: Context): RecyclerView.OnItemTouchListener {

    private val gestureDetector = GestureDetector(context, object: GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return true
        }
    })

    abstract fun onItemSelected(view: View, position: Int)

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        if(gestureDetector.onTouchEvent(e)) {
            val touchedView = rv?.findChildViewUnder(e!!.x, e.y) ?: return false
            onItemSelected(touchedView, rv.getChildAdapterPosition(touchedView))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
        throw UnsupportedOperationException("Not implemented")
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        throw UnsupportedOperationException("Not implemented")
    }

}