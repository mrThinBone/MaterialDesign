package com.example.android.myinstagram

import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewTreeObserver
import com.example.android.myinstagram.adapter.CommentAdapter
import com.example.android.myinstagram.anim.CommentItemAnimator
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity: AppCompatActivity() {

    private var mAdapter: CommentAdapter? = null
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        mAdapter = CommentAdapter(resources)

        comment_list.layoutManager = LinearLayoutManager(this)
        comment_list.itemAnimator = CommentItemAnimator()
        comment_list.adapter = mAdapter

        btn_send.preCondition { edt_comment.text.isNotEmpty() }
        btn_send.setOnClickListener{
            val comment = edt_comment.text.toString()
            edt_comment.setText("")
            mAdapter?.addComment(comment)
            comment_list.scrollToPosition(mAdapter!!.itemCount-1)
            handler.postDelayed({
                btn_send.done()
            }, 3000)
        }
    }

    override fun onResume() {
        super.onResume()
        comment_list.scrollToPosition(mAdapter!!.itemCount-1)
        container.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }

    override fun onPause() {
        super.onPause()
        container.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
    }

    private val globalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
        private val rect = Rect()

        override fun onGlobalLayout() {
            container.getWindowVisibleDisplayFrame(rect)
            val screenHeight = container.rootView.height

            val keypadHeight = screenHeight - rect.bottom
            if(keypadHeight > screenHeight * 0.15) {
                comment_list.scrollToPosition(mAdapter!!.itemCount-1)
            }
        }

    }
}