package com.example.administrator.yoursecret.Record;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/4/12.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int left;
    private int right;
    private int top;
    private int bottom;

    public SpaceItemDecoration(int left, int right, int top, int bottom){
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.top = top;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(left,top,right,bottom);
    }
}
