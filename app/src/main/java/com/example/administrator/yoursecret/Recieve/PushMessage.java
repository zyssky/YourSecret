package com.example.administrator.yoursecret.Recieve;

import android.net.Uri;

/**
 * Created by Administrator on 2017/4/16.
 */

public class PushMessage {

    private Uri imagePath;
    private String title;
    private int commentsCount;
    private Uri content;

    public Uri getImagePath() {
        return imagePath;
    }

    public void setImagePath(Uri imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Uri getContent() {
        return content;
    }

    public void setContent(Uri content) {
        this.content = content;
    }
}
