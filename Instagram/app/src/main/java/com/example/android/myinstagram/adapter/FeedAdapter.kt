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
import com.example.android.myinstagram.FeedItemAnimator
import com.example.android.myinstagram.R
import com.example.android.myinstagram.model.FeedItem

class FeedAdapter(res: Resources): RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    private var listener: FeedActionListener? = null
    private val itemAnimator = FeedItemAnimator()

    fun setActionListener(l: FeedActionListener) {
        listener = l
    }

    fun item(pos: Int): FeedItem = feedItems[pos]

    private val feedItems = listOf(
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

        private val vLikeCount = itemView.findViewById<TextSwitcher>(R.id.like_count)
        private val vImage = itemView.findViewById<ImageView>(R.id.image)
        private val vCaption = itemView.findViewById<TextView>(R.id.caption)
        private val vBtnLike = itemView.findViewById<ImageView>(R.id.like)
        init {
            vBtnLike.setOnClickListener(this)
            itemView.findViewById<ImageView>(R.id.comment).setOnClickListener(this)
            itemView.findViewById<ImageView>(R.id.more).setOnClickListener({})
        }

        fun bind(feed: FeedItem) {
            val likeCountStr = "${feed.likeCount} likes"

            vImage.setImageResource(feed.imgRes)
            vCaption.text = feed.caption
            vLikeCount.setText(likeCountStr)
            vBtnLike.setImageResource(if(feed.liked) R.drawable.ic_heart_red else R.drawable.ic_heart_outline_grey)
        }

        fun animateLike(likeCount: Int) {
            itemAnimator.animateHeartButton(vBtnLike, {})
            itemAnimator.animateLikeCounter(vLikeCount, likeCount)
        }

        override fun onClick(view: View) {
            val id = view.id
            if(id == R.id.like) {
                listener?.like(this, adapterPosition)
            }
        }
    }
}