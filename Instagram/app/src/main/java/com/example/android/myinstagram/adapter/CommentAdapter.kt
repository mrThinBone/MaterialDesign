package com.example.android.myinstagram.adapter

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.myinstagram.R
import com.example.android.myinstagram.model.CommentItem
import jp.wasabeef.glide.transformations.CropCircleTransformation

class CommentAdapter(resources: Resources) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private var userPhotos: Array<String> = resources.getStringArray(R.array.user_photos)
    private val comments = ArrayList<CommentItem>()
    private var userPhotoIndex = 0

    init {
        userPhotos.forEachIndexed { index, s ->
            comments.add(CommentItem(s, dummyComment(index)))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false))
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val vComment = itemView.findViewById<TextView>(R.id.comment)
        private val vAvatar = itemView.findViewById<ImageView>(R.id.avatar)

        fun bind(comment: CommentItem) {
            Glide.with(itemView.context)
                    .load(comment.avatar)
                    .apply(RequestOptions.bitmapTransform(CropCircleTransformation()))
                    .into(vAvatar)
            vComment.text = comment.text
        }
    }

    fun addComment(text: String) {
        comments.add(CommentItem(userPhotos[userPhotoIndex++], text))
        if(userPhotoIndex == userPhotos.size) userPhotoIndex = 0
        notifyItemInserted(comments.size - 1)
    }

    private fun dummyComment(gen: Int): String = when(gen % 3) {
        0 -> "Lorem ipsum dolor sit amet, consectetur adipisicing elit."
        1 -> "Cupcake ipsum dolor sit amet bear claw."
        else -> "Cupcake ipsum dolor sit. Amet gingerbread cupcake. Gummies ice cream dessert icing marzipan apple pie dessert sugar plum."
    }
}