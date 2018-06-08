package com.example.android.myinstagram

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import com.example.android.myinstagram.adapter.FeedAdapter
import com.example.android.myinstagram.anim.FeedItemAnimator
import com.example.android.myinstagram.anim.IntroAnimator
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), FeedActionListener {

    private var feedAdapter :FeedAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        feedAdapter = FeedAdapter(resources)
        feedAdapter?.setActionListener(this)

        feed_list.layoutManager = LinearLayoutManager(this)
        feed_list.itemAnimator = FeedItemAnimator()
        feed_list.adapter = feedAdapter

        appbar.setExpanded(false)
        Handler().postDelayed({
            fab.translationY = 2f * resources.getDimensionPixelOffset(R.dimen.btn_fab_size)
            val intro = IntroAnimator()
            intro.start(appbar, ivLogo, { feedAdapter?.updateFeed(true) })
        }, 100)
    }

    override fun like(viewHolder: FeedAdapter.FeedViewHolder?, pos: Int) {
        val feedItem = feedAdapter?.item(pos)
        if(feedItem != null && !feedItem.liked) {
            feedItem.like()
            feedAdapter?.notifyItemChanged(pos)
        }
    }

    override fun comment(view: FeedAdapter.FeedViewHolder?, pos: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun more(view: FeedAdapter.FeedViewHolder?, pos: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun report(view: FeedAdapter.FeedViewHolder?, pos: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun share(view: FeedAdapter.FeedViewHolder?, pos: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun copyURL(view: FeedAdapter.FeedViewHolder?, pos: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
