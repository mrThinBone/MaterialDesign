package com.example.android.design1.recycler

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.android.design1.R

class ProductItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val paddingRect = Rect()

    init {
        val padding = context.resources.getDimension(R.dimen.product_margin).toInt()
        paddingRect.set(padding, 0, padding, 0)
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        outRect?.set(paddingRect)
    }
}