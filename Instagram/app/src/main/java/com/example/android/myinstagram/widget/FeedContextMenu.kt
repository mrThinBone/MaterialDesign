package com.example.android.myinstagram.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.android.myinstagram.R
import com.example.android.myinstagram.Utils
import kotlinx.android.synthetic.main.view_context_menu.view.*

class FeedContextMenu(context: Context, listener: OnClickListener): LinearLayout(context) {
    private val mWidth = Utils.dpToPx(240)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_context_menu, this, true)
        setBackgroundResource(R.drawable.bg_container_shadow)
        orientation = VERTICAL
        layoutParams = LayoutParams(mWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
        btn_report.setOnClickListener(listener)
        btn_share_photo.setOnClickListener(listener)
        btn_copy_share_url.setOnClickListener(listener)
        btn_cancel.setOnClickListener(listener)
    }

    fun dismiss() {
        (parent as ViewGroup).removeView(this)
    }
}