package com.example.vinhtv.myplaid.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.example.vinhtv.myplaid.R
import com.example.vinhtv.myplaid.datasource.FeedRepository
import com.example.vinhtv.myplaid.ui.adapter.FeedAdapter
import com.example.vinhtv.myplaid.ui.model.Feed
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class HomeActivity: AppCompatActivity() {

    private val mAdapter = FeedAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if(position == 0) 2 else 1
            }
        }
        feed_list.layoutManager = gridLayoutManager

        feed_list.adapter = mAdapter
        testApi()
    }

    private fun testApi() = launch(UI) {
        val repository = FeedRepository()
        val task = async(CommonPool) { repository.fetch() }
        val feeds = task.await()
        feeds?.let {
            mAdapter.add(it.subList(0,6).map { Feed(it.images.best(), it.animated) })
        }
    }

}