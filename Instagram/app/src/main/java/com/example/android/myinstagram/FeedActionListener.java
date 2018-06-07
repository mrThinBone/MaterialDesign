package com.example.android.myinstagram;

import com.example.android.myinstagram.adapter.FeedAdapter;

public interface FeedActionListener {

    void like(FeedAdapter.FeedViewHolder viewHolder, int pos);
    void comment(FeedAdapter.FeedViewHolder view, int pos);
    void more(FeedAdapter.FeedViewHolder view, int pos);
    void report(FeedAdapter.FeedViewHolder view, int pos);
    void share(FeedAdapter.FeedViewHolder view, int pos);
    void copyURL(FeedAdapter.FeedViewHolder view, int pos);

}
