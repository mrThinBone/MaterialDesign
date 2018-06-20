package com.example.vinhtv.myplaid.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.vinhtv.myplaid.R
import com.example.vinhtv.myplaid.datasource.FeedRepository
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch

class HomeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        button_fetch.setOnClickListener { testApi() }
    }

    private fun testApi() = launch(CommonPool) {
        val repository = FeedRepository()
        val feeds = repository.fetch()
        feeds?.let {
            val number = 1+1
            Log.d("vinhtv", "operation success $number")
        }
    }

}