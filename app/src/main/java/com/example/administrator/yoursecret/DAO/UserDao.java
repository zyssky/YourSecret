package com.example.administrator.yoursecret.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.administrator.yoursecret.Entity.User;

/**
 * Created by Administrator on 2017/6/28.
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE authorId = :authorId")
    User getUser(String authorId);

    @Insert
    void addUser(User user);

    @Update
    void updateUser(User user);

    @Query("DELETE FROM user WHERE authorId = :authorId")
    void deleteUser(String authorId);
}
