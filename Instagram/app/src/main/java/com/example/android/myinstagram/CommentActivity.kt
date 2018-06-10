package com.example.android.myinstagram

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.android.myinstagram.adapter.CommentAdapter
import com.example.android.myinstagram.anim.CommentItemAnimator
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity: AppCompatActivity() {

    private var mAdapter: CommentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        mAdapter = CommentAdapter(resources)

        comment_list.layoutManager = LinearLayoutManager(this)
        comment_list.itemAnimator = CommentItemAnimator()
        comment_list.adapter = mAdapter

        btn_send.setOnClickListener{
            val comment = edt_comment.text.toString()
            edt_comment.setText("")
            mAdapter?.addComment(comment)
            comment_list.scrollToPosition(mAdapter!!.itemCount-1)
        }
    }

    override fun onResume() {
        super.onResume()
        comment_list.scrollToPosition(mAdapter!!.itemCount-1)
    }

}