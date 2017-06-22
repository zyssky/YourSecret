package com.example.administrator.yoursecret.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
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
    @Query("SELECT * FROM artical WHERE finished = 0 ORDER BY date DESC")
    List<Artical> getAllTempArticals();

    @Query("SELECT * FROM artical WHERE finished = 1 ORDER BY date DESC")
    List<Artical> getAllFinishedArtical();

    @Insert
    void insert(Artical artical);

    @Delete
    void delete(Artical artical);

    @Query("DELETE FROM artical WHERE uuid = :uuid")
    void delete(String uuid);

    @Update
    void update(Artical artical);

    @Query("UPDATE artical SET imageUri = :imageUri , articalHref = :articalHref WHERE uuid = :uuid")
    void update(String imageUri, String articalHref,String uuid);

    @Query("UPDATE artical SET finished = :finished WHERE uuid = :uuid")
    void update(int finished, String uuid);
}
