package com.example.administrator.yoursecret.AppManager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.administrator.yoursecret.DAO.ArticalDao;
import com.example.administrator.yoursecret.DAO.CommentDao;
import com.example.administrator.yoursecret.DAO.ImageDao;
import com.example.administrator.yoursecret.DAO.UserDao;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Entity.Comment;
import com.example.administrator.yoursecret.Entity.Image;
import com.example.administrator.yoursecret.Entity.User;

/**
 * Created by Administrator on 2017/6/21.
 */
@Database(entities = {Artical.class, Image.class, Comment.class, User.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ArticalDao articalDao();
    public abstract ImageDao imageDao();
    public abstract CommentDao commentDao();
    public abstract UserDao userDao();
}
