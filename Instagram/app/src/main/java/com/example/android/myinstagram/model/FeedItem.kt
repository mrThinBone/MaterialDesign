package com.example.android.myinstagram.model

data class FeedItem(val imgRes: Int, val caption: String, var likeCount: Int = 0, var liked: Boolean = false) {

    fun like() {
        liked = true
        likeCount++
    }
}