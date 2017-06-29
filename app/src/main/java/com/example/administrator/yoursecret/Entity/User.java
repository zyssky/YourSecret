package com.example.administrator.yoursecret.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Administrator on 2017/6/28.
 */
@Entity
public class User {
    @PrimaryKey
    public String authorId;

    public String iconPath;

    public String nickName;

    public String token;

    public String iconLocalPath;

    public String lastCommentDate;

    public boolean hasUnReadMessage;

}
