package com.example.administrator.yoursecret.Detail;

import com.example.administrator.yoursecret.MetaData.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/27.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private List<Comment> list;
    private CommentRecyclerAdapter adapter;

    public static void setView(DetailContract.View view) {
        DetailPresenter.view = view;
    }

    private static DetailContract.View view;

    private static DetailPresenter presenter;



    public static DetailPresenter getInstance(){
        if(presenter == null)
            presenter = new DetailPresenter();
        return presenter;
    }

    private DetailPresenter(){
        list = new ArrayList<>();
        list.add(new Comment());
        list.add(new Comment());
        list.add(new Comment());
        adapter = new CommentRecyclerAdapter();
        adapter.setmDatas(list);
    }

    @Override
    public void setCommentAdapter() {
        view.setCommentAdapter(adapter);
    }
}
