package com.example.administrator.yoursecret.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Administrator on 2017/5/27.
 */
@Entity
public class Comment {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String articalHref;

    public String content;

    public long date;

    public String nickName;

    public String iconPath;

    public String authorId;

    public String title;

    public String imageUri;

    public String introduction;
}
