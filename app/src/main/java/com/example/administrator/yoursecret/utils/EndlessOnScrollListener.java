package com.example.administrator.yoursecret.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by Administrator on 2017/5/27.
 */

public abstract class EndlessOnScrollListener extends RecyclerView.OnScrollListener {

    //声明一个LinearLayoutManager
    private LinearLayoutManager mLinearLayoutManager;

    public EndlessOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int totalItemCount = mLinearLayoutManager.getItemCount();
        int lastVisbleItemPos = mLinearLayoutManager.findLastVisibleItemPosition();
        int lastCompleteItemPos = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
        if(lastVisbleItemPos == totalItemCount-1 && dy>0 ){
            onLoadMore();
        }
    }

    public abstract void onLoadMore();
}
