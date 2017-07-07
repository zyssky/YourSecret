package com.example.administrator.yoursecret.Database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.administrator.yoursecret.Entity.Artical;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */
@Dao
public interface ArticalDao {
    @Query("SELECT * FROM artical WHERE finished = 0 AND isCache = 0 AND authorId = :authorId ORDER BY date DESC")
    List<Artical> getAllTempArticals(String authorId);

    @Query("SELECT * FROM artical WHERE finished = 1 AND isCache = 0 AND authorId = :authorId ORDER BY date DESC")
    List<Artical> getAllFinishedArtical(String authorId);

    @Insert
    void insert(Artical artical);

    @Query("DELETE FROM artical WHERE articalHref = :key")
    void deleteByHref(String key);

    @Query("DELETE FROM artical WHERE uuid = :key")
    void delete(String key);

    @Update
    void update(Artical artical);

    @Query("UPDATE artical SET imageUri = :imageUri , articalHref = :articalHref WHERE uuid = :uuid")
    void update(String imageUri, String articalHref,String uuid);

    @Query("UPDATE artical SET finished = :finished WHERE uuid = :uuid")
    void update(int finished, String uuid);

    @Query("DELETE FROM artical WHERE isCache = 1")
    void deleteCache();

    @Query("SELECT * FROM artical WHERE isCache = 1 ORDER BY date DESC")
    List<Artical> getCacheArticals();
}