package com.example.android.myinstagram.adapter

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextSwitcher
import android.widget.TextView
import com.example.android.myinstagram.FeedActionListener
import com.example.android.myinstagram.anim.FeedItemAnimator
import com.example.android.myinstagram.R
import com.example.android.myinstagram.model.FeedItem

class FeedAdapter(res: Resources): RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    private var listener: FeedActionListener? = null

    fun updateFeed(animation: Boolean) {
        feedItems.clear()
        if(animation) {
            var index = 0
            feedData.forEach {
                feedItems.add(it)
                notifyItemInserted(index++)
            }
        } else {
            feedItems.addAll(feedData)
            notifyDataSetChanged()
        }

    }

    fun setActionListener(l: FeedActionListener) {
        listener = l
    }

    fun item(pos: Int): FeedItem = feedItems[pos]

    private val feedItems = ArrayList<FeedItem>()
    private val feedData = listOf(
            FeedItem(R.drawable.img_feed_center_1, res.getString(R.string.caption1), 33),
            FeedItem(R.drawable.img_feed_center_2, res.getString(R.string.caption2), 1),
            FeedItem(R.drawable.img_feed_center_1, res.getString(R.string.caption1), 223),
            FeedItem(R.drawable.img_feed_center_2, res.getString(R.string.caption2), 2),
            FeedItem(R.drawable.img_feed_center_1, res.getString(R.string.caption1), 6),
            FeedItem(R.drawable.img_feed_center_2, res.getString(R.string.caption2), 8),
            FeedItem(R.drawable.img_feed_center_1, res.getString(R.string.caption1), 99)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return FeedViewHolder(inflater.inflate(R.layout.feed_item, parent, false))
    }

    override fun getItemCount(): Int = feedItems.size

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(feedItems[position])
    }

    inner class FeedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val vLikeCount = itemView.findViewById<TextSwitcher>(R.id.like_count)
        val vImage = itemView.findViewById<ImageView>(R.id.image)
        val vCaption = itemView.findViewById<TextView>(R.id.caption)
        val vBtnLike = itemView.findViewById<ImageView>(R.id.like)
        var feedItem: FeedItem? = null

        init {
            vBtnLike.setOnClickListener(this)
            itemView.findViewById<ImageView>(R.id.comment).setOnClickListener(this)
            itemView.findViewById<ImageView>(R.id.more).setOnClickListener({})
        }

        fun bind(feed: FeedItem) {
            feedItem = feed
            val likeCountStr = "${feed.likeCount} likes"

            vImage.setImageResource(feed.imgRes)
            vCaption.text = feed.caption
            vLikeCount.setCurrentText(likeCountStr)
            vBtnLike.setImageResource(if(feed.liked) R.drawable.ic_heart_red else R.drawable.ic_heart_outline_grey)
        }

        override fun onClick(view: View) {
            val id = view.id
            if(id == R.id.like) {
                listener?.like(this, adapterPosition)
            } else if(id == R.id.comment) {
                listener?.comment(this, adapterPosition)
            }
        }
    }
}