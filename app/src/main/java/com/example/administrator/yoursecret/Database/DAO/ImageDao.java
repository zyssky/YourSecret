package com.example.administrator.yoursecret.Database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.administrator.yoursecret.Entity.Image;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */
@Dao
public interface ImageDao {
    @Query("SELECT * FROM image WHERE uuid = :key")
    List<Image> getImages(String key);

    @Insert
    void insert(Image image);

    @Query("DELETE FROM image WHERE uuid = :key")
    void deleteImages(String key);

    @Delete
    void deleteImage(Image image);

    @Update
    void update(Image image);
}
