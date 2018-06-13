package com.example.android.myinstagram;

import android.view.View;

public interface FeedActionListener {

    void like(View view, int pos);
    void comment(View view, int pos);
    void report(View view, int pos);
    void share(View view, int pos);
    void copyURL(View view, int pos);

}
