package com.example.administrator.yoursecret.AppManager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.administrator.yoursecret.DAO.ArticalDao;
import com.example.administrator.yoursecret.DAO.ImageDao;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Entity.Image;

/**
 * Created by Administrator on 2017/6/21.
 */
@Database(entities = {Artical.class, Image.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ArticalDao articalDao();
    public abstract ImageDao imageDao();
}
