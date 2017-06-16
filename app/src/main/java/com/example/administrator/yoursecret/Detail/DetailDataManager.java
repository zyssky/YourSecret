package com.example.administrator.yoursecret.Detail;

import com.example.administrator.yoursecret.Entity.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/16.
 */

public class DetailDataManager {
    private static DetailDataManager instance;

    private DetailDataManager(){}

    public static DetailDataManager getInstance(){
        if(instance==null){
            instance = new DetailDataManager();
        }
        return instance;
    }

    public static void onDestroy(){
        instance = null;
    }

    private List<Comment> list;
    private CommentRecyclerAdapter adapter;

    public CommentRecyclerAdapter getAdapter(){
        if(adapter == null){
            adapter = new CommentRecyclerAdapter();
            adapter.setmDatas(getList());
        }
        return adapter;
    }

    private List<Comment> getList(){
        if(list == null){
            list = new ArrayList<>();
        }
        return list;
    }
}
