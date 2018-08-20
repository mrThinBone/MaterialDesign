package com.example.vinhtv.myplaid.ui.adapter

import android.animation.ObjectAnimator
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.addListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.vinhtv.myplaid.R
import com.example.vinhtv.myplaid.ext.DribbleTarget
import com.example.vinhtv.myplaid.ui.model.Feed
import com.example.vinhtv.myplaid.ui.widget.BadgedFourThreeImageView
import com.example.vinhtv.myplaid.ui.widget.PlayGifTouch
import com.example.vinhtv.myplaid.util.AnimUtils
import com.example.vinhtv.myplaid.util.ObservableColorMatrix

class FeedAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val feeds: ArrayList<Feed> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false))
    }

    override fun getItemCount() = feeds.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(feeds[position])
    }

    fun add(data: List<Feed>) {
        feeds.addAll(data)
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: BadgedFourThreeImageView = itemView as BadgedFourThreeImageView

        init {
            itemView.setOnTouchListener(PlayGifTouch())
            itemView.setOnClickListener{  }
        }

        fun bind(feed: Feed) {
            Glide.with(itemView)
                    .load(feed.imageUrl)
                    .listener(object : RequestListener<Drawable> {

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            if (feed.hasFadeIn) return false
                            image.setHasTransientState(true)
                            val cm = ObservableColorMatrix()
                            val saturation = ObjectAnimator.ofFloat(cm, ObservableColorMatrix.SATURATION, 0f, 1f)
                            saturation.addUpdateListener{ image.colorFilter = ColorMatrixColorFilter(cm) }

                            saturation.duration = 2000
                            saturation.interpolator = AnimUtils.getFastOutLinearInInterpolator(itemView.context)
                            saturation.addListener {
                                image.clearColorFilter()
                                image.setHasTransientState(false)
                            }
                            saturation.start()
                            feed.hasFadeIn = true
                            return false
                        }

                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                    })
                    .transition(withCrossFade())
                    .into(DribbleTarget(image, false))
            // need both placeholder & background to prevent seeing through shot as it fades in

            // need a unique transition name per shot, let's use it's url
            image.transitionName = feed.imageUrl
        }
    }

    class LoadmoreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

}