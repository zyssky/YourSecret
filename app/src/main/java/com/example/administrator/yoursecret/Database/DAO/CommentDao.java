package com.example.administrator.yoursecret.Database.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.administrator.yoursecret.Entity.Comment;

import java.util.List;

/**
 * Created by Administrator on 2017/6/24.
 */

@Dao
public interface CommentDao {
    @Insert
    void addComment(Comment comment);

    @Query("SELECT * FROM comment where authorId = :authorId ORDER BY date DESC")
    List<Comment> getComments(String authorId);
}
