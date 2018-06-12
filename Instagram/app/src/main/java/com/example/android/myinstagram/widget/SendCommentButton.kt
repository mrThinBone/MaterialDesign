package com.example.android.myinstagram.widget

import android.content.Context
import android.graphics.drawable.Animatable
import android.support.v7.widget.AppCompatImageButton
import android.util.AttributeSet
import android.view.View
import com.example.android.myinstagram.R

class SendCommentButton: AppCompatImageButton {

    private val statePlay = intArrayOf(R.attr.state_send, -R.attr.state_loading, -R.attr.state_done)
    private val stateLoading = intArrayOf(-R.attr.state_send, R.attr.state_loading, -R.attr.state_done)
    private val stateDone = intArrayOf(-R.attr.state_send, -R.attr.state_loading, R.attr.state_done)

    private var sending = false

    private var clickListener: View.OnClickListener? = null
    private lateinit var triggerCondition: () -> Boolean

    constructor(context: Context): this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        setImageResource(R.drawable.asl_send_comment)
        super.setOnClickListener {
            if(!sending && triggerCondition()) {
                sending = true
                clickListener?.onClick(it)
                setImageResource(R.drawable.avd_three_dots)
                (drawable as Animatable).start()
            }
        }
    }

    fun preCondition(condition: () -> Boolean) {
        triggerCondition = condition
    }

    override fun setOnClickListener(l: OnClickListener?) {
        clickListener = l
    }

    fun done() {
        (drawable as Animatable).stop()
        setImageResource(R.drawable.asl_send_comment)
        setImageState(stateLoading, true)
        setImageState(stateDone, true)
        postDelayed({
            setImageState(statePlay, true)
            sending = false
        }, 500)
    }

}